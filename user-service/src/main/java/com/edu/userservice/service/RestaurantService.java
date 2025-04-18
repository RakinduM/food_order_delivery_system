package com.edu.userservice.service;


import com.edu.userservice.dto.ResAuthResponse;
import com.edu.userservice.dto.ResRegRequest;
import com.edu.userservice.dto.RestaurantRes;

public interface RestaurantService {
    ResAuthResponse register(ResRegRequest request);
    ResAuthResponse authenticate(String email, String password);
    RestaurantRes getUserByEmail(String email);
}
