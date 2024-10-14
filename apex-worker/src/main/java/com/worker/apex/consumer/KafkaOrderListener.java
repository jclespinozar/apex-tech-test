package com.worker.apex.consumer;

import com.worker.apex.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderListener {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(KafkaOrderListener.class);

    @KafkaListener(topics = "${kafka.topic.send-order}", groupId = "${kafka.consumer.group.id}")
    public void consumeOrder(String message) {
        logger.info("Mensaje recibido: {}", message);
        orderService.processOrder(message)
                .subscribe(
                        order -> logger.info("Pedido procesado: {}", order),
                        error -> logger.error("Error procesando el pedido: {}", error.getMessage())
                );
    }
}
