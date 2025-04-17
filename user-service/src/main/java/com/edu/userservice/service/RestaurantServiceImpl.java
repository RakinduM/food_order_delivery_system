package com.edu.userservice.service;

import com.edu.userservice.dto.*;
import com.edu.userservice.model.Restaurant;
import com.edu.userservice.repository.RestaurantRepository;
import com.edu.userservice.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResAuthResponse register(ResRegRequest request) {
        // Check if username already exists
        if (restaurantRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Create and save the new user
        var user = Restaurant.builder()
                .restaurantName(request.getRestaurantName())
                .restaurantAdmin(request.getRestaurantAdmin())
                .type(request.getType())
                .address(request.getAddress())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .businessDoc(request.getBusinessDoc())
                .isAvailable(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        restaurantRepository.save(user);

        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);

        return ResAuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }

    @Override
    public ResAuthResponse authenticate(String email, String password) {
        // Use the AuthenticationManager to authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // If authentication is successful, retrieve the user
        var user = restaurantRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);

        return ResAuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }

    @Override
    public RestaurantRes getUserByEmail(String email) {
        Restaurant restaurant = restaurantRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        BigDecimal numberAsBigDecimal = BigDecimal.ZERO;
        try {
            numberAsBigDecimal = new BigDecimal(restaurant.getPhoneNumber()); // assuming 'phoneNumber' is the 'number' field
        } catch (NumberFormatException e) {
            // Handle invalid number format if needed
        }

        return RestaurantRes.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantAdmin(restaurant.getRestaurantAdmin())
                .phoneNumber(restaurant.getPhoneNumber())
                .build();
    }
}

