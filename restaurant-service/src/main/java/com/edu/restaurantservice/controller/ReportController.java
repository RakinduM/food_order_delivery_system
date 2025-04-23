package com.edu.restaurantservice.controller;

import com.edu.restaurantservice.dto.FoodSalesReportDTO;
import com.edu.restaurantservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/food-sales")
    public ResponseEntity<List<FoodSalesReportDTO>> getFoodSalesReport(
            @RequestParam String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<FoodSalesReportDTO> report = reportService.generateFoodSalesReport(restaurantId, startDate, endDate);
        return ResponseEntity.ok(report);
    }
}