package com.edu.deliveryservice.controller;

import com.edu.deliveryservice.dto.*;
import com.edu.deliveryservice.model.Location;
import com.edu.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/assign")
    public ResponseEntity<DeliveryResponseDTO> assign(@RequestBody AssignDeliveryRequestDTO dto) {
        return ResponseEntity.ok(deliveryService.assignDelivery(dto));
    }

    @PostMapping("/assign-auto")
    public ResponseEntity<DeliveryResponseDTO> autoAssign(@RequestBody AssignAutoDeliveryRequestDTO dto) {
        return ResponseEntity.ok(deliveryService.autoAssignDeliveryWithDriverResponse(dto));
    }

    @PostMapping("/track")
    public ResponseEntity<DeliveryResponseDTO> track(@RequestBody TrackingUpdateDTO dto) {
        return ResponseEntity.ok(deliveryService.updateTracking(dto));
    }

    @GetMapping("/track/{orderId}")
    public ResponseEntity<List<Location>> getTracking(@PathVariable String orderId) {
        return ResponseEntity.ok(deliveryService.getTrackingHistory(orderId));
    }

    @GetMapping("/location/{orderId}")
    public ResponseEntity<Location> getCurrent(@PathVariable String orderId) {
        return deliveryService.getCurrentLocation(orderId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<Void> updateStatus(@PathVariable String orderId, @RequestParam String status) {
        deliveryService.updateStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/complete/{orderId}")
    public ResponseEntity<Void> complete(@PathVariable String orderId) {
        deliveryService.completeDelivery(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/driver-response")
    public ResponseEntity<Void> handleDriverResponse(@RequestBody DriverResponseDTO dto) {
        deliveryService.handleDriverResponse(dto);
        return ResponseEntity.ok().build();
    }
}
