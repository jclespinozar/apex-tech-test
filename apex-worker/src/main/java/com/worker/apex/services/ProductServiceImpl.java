package com.worker.apex.services;

import com.worker.apex.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${app.apex.apex-api-product.url}")
    private String apexApiProductUrl;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Mono<List<Order.Product>> fetchProductData(List<Long> productIds) {
        logger.info("Requesting product data for product IDs: {}", productIds);
        return webClientBuilder.build()
                .post()
                .uri(apexApiProductUrl)
                .bodyValue(productIds)
                .retrieve()
                .bodyToFlux(Order.Product.class)
                .doOnNext(product -> logger.info("Fetched product: {}", product.toString()))
                .collectList()
                .onErrorResume(e -> {
                    //log.error("Error fetching product data for productIds: {}", productIds, e);
                    return Mono.just(Collections.emptyList());
                });
    }
}
