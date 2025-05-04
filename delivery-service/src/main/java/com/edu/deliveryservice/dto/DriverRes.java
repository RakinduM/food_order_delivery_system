package com.edu.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverRes {
    private String id;
    private String firstName;
    private String lastName;
    private String nic;
    private Boolean isAvailable;
}