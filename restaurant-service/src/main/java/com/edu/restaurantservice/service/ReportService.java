package com.edu.restaurantservice.service;

import com.edu.restaurantservice.dto.FoodSalesReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<FoodSalesReportDTO> generateFoodSalesReport(String restaurantId, LocalDate startDate, LocalDate endDate);
}