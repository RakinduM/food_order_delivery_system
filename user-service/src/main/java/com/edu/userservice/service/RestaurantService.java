package com.edu.userservice.service;


import com.edu.userservice.dto.ResAuthResponse;
import com.edu.userservice.dto.ResRegRequest;
import com.edu.userservice.dto.RestaurantRes;

import java.util.List;

public interface RestaurantService {
    ResAuthResponse register(ResRegRequest request);
    ResAuthResponse authenticate(String email, String password);
    List<RestaurantRes> getAllRestaurants();
    RestaurantRes getUserById(String id);
    RestaurantRes getUserByEmail(String email);
}
