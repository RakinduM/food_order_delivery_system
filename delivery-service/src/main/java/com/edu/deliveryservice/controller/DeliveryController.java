package com.edu.deliveryservice.controller;

import com.edu.deliveryservice.dto.*;
import com.edu.deliveryservice.model.Location;
import com.edu.deliveryservice.service.DeliveryService;
import com.edu.deliveryservice.service.DriverLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final SimpMessagingTemplate messagingTemplate;
    private final DriverLocationService driverLocationService;

    @MessageMapping("/hello")
    public void hello(String message) {
        log.info("Received message: {}", message);
        messagingTemplate.convertAndSend("/topic/hello", "GG Hello World");
    }

    @MessageMapping("/driver/location") // e.g. "/app/driver/location"
    public void receiveLocation(DriverLocationDTO location) {
        driverLocationService.updateDriverLocation(location.getDriverId(), location);
    }

    @MessageMapping("/delivery/assign")
    public void assign(AssignDeliveryRequestDTO dto) {
        DeliveryResponseDTO response = deliveryService.assignDelivery(dto);
        log.info("Assign request received: {}", dto);
        messagingTemplate.convertAndSend("/topic/delivery/" + dto.getOrderId(), response);
    }

    @MessageMapping("/delivery/assign-auto")
    public DeliveryResponseDTO autoAssign(AssignAutoDeliveryRequestDTO dto) {
        log.info("Auto-assign request received: {}", dto);
        return deliveryService.autoAssignDeliveryWithDriverResponse(dto);
    }


    @MessageMapping("/delivery/track-update")
    public DeliveryResponseDTO track(TrackingUpdateDTO dto) {
        return deliveryService.updateTracking(dto);
    }


    @MessageMapping("/delivery/track-history")
    public List<Location> getTracking(String orderId) {
        return deliveryService.getTrackingHistory(orderId);
    }


    @MessageMapping("/delivery/location")
    public Location getCurrent(String orderId) {
        return deliveryService.getCurrentLocation(orderId)
                .orElse(null);
    }


    @MessageMapping("/delivery/status")
    public void updateStatus(UpdateStatusDTO dto) {
        deliveryService.updateStatus(dto.getOrderId(), dto.getStatus());
        messagingTemplate.convertAndSend("/topic/delivery/" + dto.getOrderId() + "/status", dto.getStatus());
    }

    // Handler for marking the delivery as complete
    @MessageMapping("/delivery/complete")
    public void complete(String orderId) {
        deliveryService.completeDelivery(orderId);
    }


    // Handler for receiving driver responses
    @MessageMapping("/delivery/driver-response")
    public void handleDriverResponse(DriverResponseDTO dto) {
        deliveryService.handleDriverResponse(dto);
    }

}
