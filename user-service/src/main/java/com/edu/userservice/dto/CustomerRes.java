package com.edu.userservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRes {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private BigDecimal number; // changed from String to float
}