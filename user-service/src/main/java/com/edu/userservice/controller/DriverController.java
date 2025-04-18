package com.edu.userservice.controller;

import com.edu.userservice.dto.AuthRequest;
import com.edu.userservice.dto.AuthResponse;
import com.edu.userservice.dto.CustomerRegRequest;
import com.edu.userservice.dto.DriverRegRequest;
import com.edu.userservice.service.DriverService;
import com.edu.userservice.service.GcsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final GcsService gcsService;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<AuthResponse> register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("number") String number,
            @RequestParam("nicImage") MultipartFile nicImage
    ) {
        try {
            String nicImageUrl = gcsService.uploadFile(nicImage);

            DriverRegRequest request = DriverRegRequest.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .nic(nicImageUrl)
                    .email(email)
                    .phoneNumber(number)
                    .password(password)
                    .build();

            AuthResponse response = driverService.register(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AuthResponse.builder().message("Error: " + e.getMessage()).build());
        }
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
