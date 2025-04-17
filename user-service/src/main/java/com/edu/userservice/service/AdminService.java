package com.edu.userservice.service;

import com.edu.userservice.dto.*;

import java.util.List;

public interface AdminService {
    AuthResponse register(AdminRegRequest request);
    AuthResponse authenticate(String username, String password);
    List<AdminRes> getUserByUsername();
    List<CustomerRes> getAllCustomers();
    List<DriverRes> getAllDrivers();
    List<RestaurantRes> getAllRestaurants();
}
