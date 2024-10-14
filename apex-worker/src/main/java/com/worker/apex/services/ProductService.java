package com.worker.apex.services;

import com.worker.apex.models.Order;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {
    Mono<List<Order.Product>> fetchProductData(List<Long> productIds);
}
