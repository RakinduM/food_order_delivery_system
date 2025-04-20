package com.edu.orderservice.dto;

import com.edu.orderservice.document.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private String id;
    private String customerId;
    private String restaurantId;
    private double totalAmount;
    private OrderStatus status;
    private List<OrderItemRequestDTO> items;
    private Instant createdAt;
    private Instant updatedAt;
}
