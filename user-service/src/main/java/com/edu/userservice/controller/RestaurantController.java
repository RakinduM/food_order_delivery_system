package com.edu.userservice.controller;

import com.edu.userservice.dto.*;
import com.edu.userservice.service.GcsService;
import com.edu.userservice.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final GcsService gcsService;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<ResAuthResponse> register(
            @RequestParam("resName") String restaurantName,
            @RequestParam("resAdmin") String restaurantAdmin,
            @RequestParam("type") String type,
            @RequestParam("address") String address,
            @RequestParam("email") String email,
            @RequestParam("number") String number,
            @RequestParam("busDoc") MultipartFile busDoc,
            @RequestParam("isAvailable") Boolean isAvailable,
            @RequestParam("password") String password

    ) {
        try {
            String busDocUrl = gcsService.uploadFile(busDoc);

            ResRegRequest request = ResRegRequest.builder()
                    .restaurantName(restaurantName)
                    .restaurantAdmin(restaurantAdmin)
                    .type(type)
                    .address(address)
                    .email(email)
                    .phoneNumber(number)
                    .businessDoc(busDocUrl)
                    .isAvailable(isAvailable)
                    .password(password)
                    .build();

            ResAuthResponse response = restaurantService.register(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResAuthResponse.builder().message("Error: " + e.getMessage()).build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResAuthResponse> authenticate(
            @Valid @RequestBody ResAuthRequest request
    ) {
        return ResponseEntity.ok(restaurantService.authenticate(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/")
    public ResponseEntity<List<RestaurantRes>> getRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantRes> getRestaurant(@PathVariable String id) {
        return ResponseEntity.ok(restaurantService.getUserById(id));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(restaurantService.getUserByEmail(email));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
