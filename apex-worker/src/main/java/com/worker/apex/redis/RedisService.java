package com.worker.apex.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

    private static final String FAILED_ORDERS_KEY = "failedOrders";

    @Autowired
    private RedisTemplate<String, FailedOrder> redisTemplate;

    public void saveFailedOrder(FailedOrder failedOrder) {
        redisTemplate.opsForHash().put(FAILED_ORDERS_KEY, failedOrder.getOrderId(), failedOrder);
    }

    public FailedOrder getFailedOrder(String orderId) {
        return (FailedOrder) redisTemplate.opsForHash().get(FAILED_ORDERS_KEY, orderId);
    }

    public void deleteFailedOrder(String orderId) {
        redisTemplate.opsForHash().delete(FAILED_ORDERS_KEY, orderId);
    }

    public Optional<Integer> getRetryCount(String orderId) {
        FailedOrder failedOrder = (FailedOrder) redisTemplate.opsForHash().get(FAILED_ORDERS_KEY, orderId);
        return Optional.ofNullable(failedOrder).map(FailedOrder::getRetryCount);
    }
}
