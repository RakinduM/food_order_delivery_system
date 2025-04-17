package com.edu.userservice.controller;

import com.edu.userservice.dto.*;
import com.edu.userservice.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AdminRegRequest request
    ) {
        return new ResponseEntity<>(adminService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(adminService.authenticate(request.getUsername(), request.getPassword()));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserByUsername() {
        try {
            return ResponseEntity.ok(adminService.getUserByUsername());
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/admin/customers")
    public ResponseEntity<List<CustomerRes>> getCustomers() {
        return ResponseEntity.ok(adminService.getAllCustomers());
    }

    @GetMapping("/admin/drivers")
    public ResponseEntity<List<DriverRes>> getDrivers() {
        return ResponseEntity.ok(adminService.getAllDrivers());
    }

    @GetMapping("/admin/restaurants")
    public ResponseEntity<List<RestaurantRes>> getRestaurants() {
        return ResponseEntity.ok(adminService.getAllRestaurants());
    }
}
