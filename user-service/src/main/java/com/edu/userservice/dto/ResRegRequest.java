package com.edu.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResRegRequest {
    @NotBlank(message = "Restaurent name is required")
    @Size(min = 4, max = 20, message = "Restaurent name must be between 4 and 20 characters")
    private String restaurantName;

    @NotBlank(message = "Restaurent Admin name is required")
    @Size(min = 4, max = 20, message = "Last name must be between 4 and 20 characters")
    private String restaurantAdmin;

    @NotBlank(message = "Type is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String type;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Number is required")
    @Size(min = 5, max = 50, message = "Number must be between 5 and 50 characters")
    private String phoneNumber;

    @NotBlank(message = "Business Documents is required")
    private String businessDoc;

    @NotBlank(message = "Availability is required")
    private Boolean isAvailable;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
