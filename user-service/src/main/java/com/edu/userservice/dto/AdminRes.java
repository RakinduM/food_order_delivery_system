package com.edu.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRes {
//    private String id;
//    private String username;
//    private String email;
    private CustomerRes customer;
    private DriverRes driver;
    private RestaurantRes restaurant;
}
