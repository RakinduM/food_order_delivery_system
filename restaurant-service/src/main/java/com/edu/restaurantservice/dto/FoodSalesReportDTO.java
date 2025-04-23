package com.edu.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodSalesReportDTO {
    private String menuItemId;
    private String menuItemName;
    private Long quantitySold;
    private Double totalRevenue;
    private LocalDate startDate;
    private LocalDate endDate;
}