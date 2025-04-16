package com.edu.userservice.controller;

import com.edu.userservice.dto.AuthRequest;
import com.edu.userservice.dto.AuthResponse;
import com.edu.userservice.dto.CustomerRegRequest;
import com.edu.userservice.dto.DriverRegRequest;
import com.edu.userservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody DriverRegRequest request
    ) {
        return new ResponseEntity<>(driverService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(driverService.authenticate(request.getUsername(), request.getPassword()));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(driverService.getUserByUsername(username));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
