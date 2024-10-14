package com.worker.apex.services;

import com.worker.apex.models.Order;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<Order> processOrder(String message);
}
