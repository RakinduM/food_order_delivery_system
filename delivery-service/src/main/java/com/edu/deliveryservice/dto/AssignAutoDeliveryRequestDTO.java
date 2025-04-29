package com.edu.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignAutoDeliveryRequestDTO {
    private String orderId;
    private double restaurantLatitude;
    private double restaurantLongitude;
    private double customerLatitude;
    private double customerLongitude;
}
