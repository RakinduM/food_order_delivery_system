package com.edu.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResAuthResponse {
    private String token;
    private String email;
    private String restaurantId;
    private String message;
}
