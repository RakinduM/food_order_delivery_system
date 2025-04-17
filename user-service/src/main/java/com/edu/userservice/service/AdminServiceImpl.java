package com.edu.userservice.service;

import com.edu.userservice.dto.*;
import com.edu.userservice.model.Admin;
import com.edu.userservice.model.Customer;
import com.edu.userservice.model.Driver;
import com.edu.userservice.model.Restaurant;
import com.edu.userservice.repository.AdminRepository;
import com.edu.userservice.repository.CustomerRepository;
import com.edu.userservice.repository.DriverRepository;
import com.edu.userservice.repository.RestaurantRepository;
import com.edu.userservice.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public AuthResponse register(AdminRegRequest request) {
        // Check if username already exists
        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Create and save the new user
        var user = Admin.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        adminRepository.save(user);

        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

    @Override
    public AuthResponse authenticate(String username, String password) {
        // Use the AuthenticationManager to authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // If authentication is successful, retrieve the user
        var user = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

    @Override
    public List<AdminRes> getUserByUsername() {
        List<AdminRes> allUsers = new ArrayList<>();

        // Map customers
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            CustomerRes customerRes = CustomerRes.builder()
                    .id(customer.getId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .username(customer.getUsername())
                    .email(customer.getEmail())

                    .build();

            allUsers.add(AdminRes.builder()
                    .customer(customerRes)
                    .build());
        }

        // Map drivers
        List<Driver> drivers = driverRepository.findAll();
        for (Driver driver : drivers) {
            DriverRes driverRes = DriverRes.builder()
                    .id(driver.getId())
                    .firstName(driver.getFirstName())
                    .lastName(driver.getLastName())
                    .nic(driver.getNic())
                    .build();

            allUsers.add(AdminRes.builder()
                    .driver(driverRes)
                    .build());
        }

        // Map restaurants
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            RestaurantRes restaurantRes = RestaurantRes.builder()
                    .id(restaurant.getId())
                    .restaurantName(restaurant.getRestaurantName())
                    .restaurantAdmin(restaurant.getRestaurantAdmin())
                    .phoneNumber(restaurant.getPhoneNumber())
                    .build();

            allUsers.add(AdminRes.builder()
                    .restaurant(restaurantRes)
                    .build());
        }

        return allUsers;
    }

    public List<CustomerRes> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerRes.class))
                .collect(Collectors.toList());
    }

    public List<DriverRes> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(driver -> modelMapper.map(driver, DriverRes.class))
                .collect(Collectors.toList());
    }

    public List<RestaurantRes> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantRes.class))
                .collect(Collectors.toList());
    }
}
