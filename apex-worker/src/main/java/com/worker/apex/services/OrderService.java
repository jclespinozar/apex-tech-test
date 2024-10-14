package com.worker.apex.services;

import com.worker.apex.models.Order;
import com.worker.apex.models.OrderMessage;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {
    Mono<Order> processOrder(String message);
}
