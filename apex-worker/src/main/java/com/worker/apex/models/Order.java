package com.worker.apex.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "orders")
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private String orderId;
    private String customerId;
    private String customerName;
    private List<Product> products;

    @Getter
    @Setter
    public static class Product {
        private String productId;
        private String name;
        private Double price;
        private Integer quantity;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", products=" + products +
                '}';
    }
}
