package com.worker.apex.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.apex.models.Client;
import com.worker.apex.models.Order;
import com.worker.apex.models.OrderMessage;
import com.worker.apex.redis.FailedOrder;
import com.worker.apex.redis.RedisService;
import com.worker.apex.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.util.List;
import java.util.stream.Collectors;

import java.time.Duration;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Mono<Order> processOrder(String message) {
        OrderMessage orderMessage = parseOrderMessage(message);
        String orderId = orderMessage.getOrderId();
        String clientId = orderMessage.getClientId();
        List<OrderMessage.ProductOrder> productOrders = orderMessage.getProducts();

        logger.info("Start processing orderId: {}, clientId: {}", orderId, clientId);
        List<Long> productIds = extractProductIds(productOrders);
        logger.info("Product IDs: {}", productIds);

        return handleClientData(clientId, orderId, message)
                .zipWith(handleProductData(productIds, orderId, message))
                .map(t -> combineOrderData(t.getT1(), t.getT2(), orderId, clientId, productOrders));
    }

    private OrderMessage parseOrderMessage(String message) {
        try {
            return objectMapper.readValue(message, OrderMessage.class);
        } catch (Exception e) {
            logger.error("Error al parsear el mensaje: {}", e.getMessage());
            throw new RuntimeException("Error parsing message", e);
        }
    }

    private List<Long> extractProductIds(List<OrderMessage.ProductOrder> productOrders) {
        return productOrders.stream()
                .map(OrderMessage.ProductOrder::getProductId)
                .collect(Collectors.toList());
    }

    private Mono<Client> handleClientData(String clientId, String orderId, String message) {
        return clientService.fetchClientData(clientId)
                .doOnNext(client -> logger.info("Client data fetched: {}", client))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(ex -> ex instanceof WebClientResponseException &&
                                ((WebClientResponseException) ex).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR))
                .doOnError(e -> handleClientError(clientId, orderId, message, e));
    }

    private void handleClientError(String clientId, String orderId, String message, Throwable e) {
        logger.error("Error fetching client data for clientId {}: {}", clientId, e.getMessage());
        redisService.saveFailedOrder(new FailedOrder(orderId, message, 1)); // Incrementar contador aquí
    }

    private Mono<List<Order.Product>> handleProductData(List<Long> productIds, String orderId, String message) {
        return productService.fetchProductData(productIds)
                .flatMap(products -> {
                    List<Long> missingProductIds = productIds.stream()
                            .filter(productId -> products.stream().noneMatch(product -> product.getProductId().equals(productId)))
                            .collect(Collectors.toList());

                    if (!missingProductIds.isEmpty()) {
                        logger.error("Missing product IDs: {}", missingProductIds);
                        redisService.saveFailedOrder(new FailedOrder(orderId, message, 1)); // Incrementar contador aquí
                    }

                    return Mono.just(products);
                })
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .doOnError(e -> handleProductError(orderId, message, e));
    }

    private void handleProductError(String orderId, String message, Throwable e) {
        logger.error("Error fetching product data, retrying...", e);
        redisService.saveFailedOrder(new FailedOrder(orderId, message, 1)); // Incrementar contador aquí
    }

    private Order combineOrderData(Client client, List<Order.Product> products, String orderId, String clientId, List<OrderMessage.ProductOrder> productOrders) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(clientId);
        order.setCustomerName(client.getName());

        List<Order.Product> finalProducts = products.stream()
                .map(product -> {
                    Integer quantity = productOrders.stream()
                            .filter(po -> po.getProductId().equals(product.getProductId()))
                            .findFirst()
                            .map(OrderMessage.ProductOrder::getQuantity)
                            .orElse(1);
                    product.setQuantity(quantity);
                    logger.info("Product {} assigned quantity: {}", product.getProductId(), quantity);
                    return product;
                })
                .collect(Collectors.toList());

        order.setProducts(finalProducts);
        logger.info("Final order: {}", order);
        return order;
    }
}
