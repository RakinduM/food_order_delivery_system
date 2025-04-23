package com.edu.restaurantservice.service;

import com.edu.restaurantservice.dto.FoodSalesReportDTO;
import com.edu.restaurantservice.repository.MenuItemRepository;
import com.edu.restaurantservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public List<FoodSalesReportDTO> generateFoodSalesReport(String restaurantId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<FoodSalesReportDTO> reports = menuItemRepository
                .getSalesReportByRestaurantAndDateRange(restaurantId, startDateTime, endDateTime);

        // Add date range to each report
        reports.forEach(report -> {
            report.setStartDate(startDate);
            report.setEndDate(endDate);
        });

        return reports;
    }
}