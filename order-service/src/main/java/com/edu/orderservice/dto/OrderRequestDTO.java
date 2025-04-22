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
    private String latitude;
    private String longitude;
    private List<OrderItemRequestDTO> items;

}
