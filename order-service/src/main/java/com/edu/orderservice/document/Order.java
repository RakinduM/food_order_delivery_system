package com.edu.orderservice.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    private String customerId;
    private String restaurantId;
    private OrderStatus status;
    private double totalAmount;

    private List<OrderItem> items;
    private Instant createdAt;
    private Instant updatedAt;
}
