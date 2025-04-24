package com.edu.deliveryservice.service;

import com.edu.deliveryservice.dto.*;
import com.edu.deliveryservice.model.Location;

import java.util.List;
import java.util.Optional;

public interface DeliveryService{
    DeliveryResponseDTO assignDelivery(AssignDeliveryRequestDTO dto);
    DeliveryResponseDTO autoAssignDeliveryWithDriverResponse(AssignAutoDeliveryRequestDTO dto);
    DeliveryResponseDTO updateTracking(TrackingUpdateDTO dto);
    List<Location> getTrackingHistory(String orderId);
    Optional<Location> getCurrentLocation(String orderId);
    void updateStatus(String orderId, String status);
    void completeDelivery(String orderId);
    void handleDriverResponse(DriverResponseDTO response);
}
