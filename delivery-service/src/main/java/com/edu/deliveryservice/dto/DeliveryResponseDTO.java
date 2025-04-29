package com.edu.deliveryservice.dto;

import com.edu.deliveryservice.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponseDTO {
    private String id;
    private String orderId;
    private String driverId;
    private String status;
    private double restaurantLatitude;
    private double restaurantLongitude;
    private double customerLatitude;
    private double customerLongitude;
    private Instant assignedAt;
    private Instant deliveredAt;
    private List<Location> tracking;
}
