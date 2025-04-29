package com.edu.userservice.controller;

import com.edu.userservice.dto.*;
import com.edu.userservice.service.DriverService;
import com.edu.userservice.service.GcsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
            @RequestParam("nicImage") MultipartFile nicImage,
            @RequestParam("isAvailable") Boolean isAvailable,
            @RequestParam("isApproved") Boolean isApproved
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
                    .isAvailable(isAvailable)
                    .isApproved(isApproved)
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

    @GetMapping("/available")
    public ResponseEntity<List<DriverRes>> getAvailableDrivers() {
        return ResponseEntity.ok(driverService.getAvailableDrivers());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(driverService.getUserByUsername(username));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PatchMapping("/{driverId}/availability")
    public ResponseEntity<String> updateDriverAvailability(
            @PathVariable String driverId,
            @RequestBody DriverUpAvailabilityRequest request
    ){
        boolean updated = driverService.updateDriverAvailability(driverId, request.getIsAvailable());
        if(updated) {
            return ResponseEntity.ok("Driver availability updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found");
        }
    }
}
