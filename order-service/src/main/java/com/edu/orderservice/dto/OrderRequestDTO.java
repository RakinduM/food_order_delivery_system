package com.edu.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private String customerId;
    private String restaurantId;
    private String customer_latitude;
    private String customer_longitude;
    private String restaurant_latitude;
    private String restaurant_longitude;
    private List<OrderItemRequestDTO> items;

}
