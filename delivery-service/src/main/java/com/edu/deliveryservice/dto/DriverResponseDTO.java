package com.edu.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponseDTO {
    private String orderId;
    private String driverId;
    private boolean accepted;
}
