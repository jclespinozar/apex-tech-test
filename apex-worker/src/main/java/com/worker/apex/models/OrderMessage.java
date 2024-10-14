package com.worker.apex.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderMessage {
    private String orderId;
    private String clientId;
    private List<ProductOrder> products;

    @Getter
    @Setter
    public static class ProductOrder {
        private Long productId;
        private int quantity;
    }
}
