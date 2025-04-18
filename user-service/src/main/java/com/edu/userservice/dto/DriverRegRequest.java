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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverRegRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 4, max = 20, message = "First name must be between 4 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 4, max = 20, message = "Last name must be between 4 and 20 characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{4,20}$", message = "Username can only contain letters, numbers, dots, underscores and hyphens")
    private String username;

    @NotBlank(message = "NIC is required")
    private String nic;

    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Number is required")
    @Size(min = 5, max = 50, message = "Number must be between 5 and 50 characters")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
