package com.edu.deliveryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "deliveries")
public class Delivery {
    @Id
    private String id;
    private String orderId;
    private String driverId;
    private String status; // READY_TO_DELIVER, DELIVERING, DELIVERED
    private double restaurantLatitude;
    private double restaurantLongitude;
    private double customerLatitude;
    private double customerLongitude;
    private Instant assignedAt;
    private Instant deliveredAt;
    private List<Location> tracking;
}
