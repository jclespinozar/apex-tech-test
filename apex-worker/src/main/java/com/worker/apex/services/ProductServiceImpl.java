package com.worker.apex.services;

import com.worker.apex.models.Order;
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

    @Override
    public Mono<List<Order.Product>> fetchProductData(List<Long> productIds) {
        return webClientBuilder.build()
                .post()
                .uri(apexApiProductUrl)
                .bodyValue(productIds)
                .retrieve()
                .bodyToFlux(Order.Product.class)
                .collectList()
                .onErrorResume(e -> {
                    //log.error("Error fetching product data for productIds: {}", productIds, e);
                    return Mono.just(Collections.emptyList());
                });
    }
}
