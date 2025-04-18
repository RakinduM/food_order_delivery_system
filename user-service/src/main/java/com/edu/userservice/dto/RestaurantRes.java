package com.edu.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRes {
    private String id;
    private String restaurantName;
    private String restaurantAdmin;
    private String phoneNumber;
}
