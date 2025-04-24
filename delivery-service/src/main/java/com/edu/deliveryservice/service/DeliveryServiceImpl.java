package com.edu.deliveryservice.service;

import com.edu.deliveryservice.config.TrackingWebSocketPublisher;
import com.edu.deliveryservice.model.Delivery;
import com.edu.deliveryservice.model.Location;
import com.edu.deliveryservice.repository.DeliveryRepository;
import com.edu.deliveryservice.util.MapboxService;
import com.edu.deliveryservice.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepo;
    private final TrackingWebSocketPublisher publisher;
    private final RestTemplate restTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final MapboxService mapboxService;

    private final Map<String, CompletableFuture<Boolean>> driverResponses = new ConcurrentHashMap<>();
    private final long DRIVER_RESPONSE_TIMEOUT = 15L;

    @Override
    public DeliveryResponseDTO assignDelivery(AssignDeliveryRequestDTO dto) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(dto.getOrderId());
        delivery.setDriverId(dto.getDriverId());
        delivery.setRestaurantLatitude(dto.getRestaurantLatitude());
        delivery.setRestaurantLongitude(dto.getRestaurantLongitude());
        delivery.setCustomerLatitude(dto.getCustomerLatitude());
        delivery.setCustomerLongitude(dto.getCustomerLongitude());
        delivery.setAssignedAt(Instant.now());
        delivery.setStatus("READY_TO_DELIVER");
        delivery.setTracking(new ArrayList<>());
        return mapToResponse(deliveryRepo.save(delivery));
    }

    @Override
    public DeliveryResponseDTO autoAssignDeliveryWithDriverResponse(AssignAutoDeliveryRequestDTO dto) {
        DriverLocationDTO[] availableDrivers = restTemplate.getForObject("http://USER-SERVICE/drivers/available", DriverLocationDTO[].class);
        if (availableDrivers == null || availableDrivers.length == 0) throw new RuntimeException("No available drivers");

        List<DriverDistanceInfo> nearbyDrivers = new ArrayList<>();
        for (DriverLocationDTO driver : availableDrivers) {
            double distance = mapboxService.calculateDistance(dto.getRestaurantLatitude(), dto.getRestaurantLongitude(), driver.getLatitude(), driver.getLongitude());
            if (distance <= mapboxService.getMaxDriverDistance()) {
                double eta = mapboxService.getEstimatedTravelTime(driver.getLatitude(), driver.getLongitude(), dto.getRestaurantLatitude(), dto.getRestaurantLongitude());
                nearbyDrivers.add(new DriverDistanceInfo(driver, distance, eta));
            }
        }

        if (nearbyDrivers.isEmpty()) throw new RuntimeException("No drivers within range");
        nearbyDrivers.sort(Comparator.comparing(DriverDistanceInfo::getDistance));
        Set<String> rejectedDriverIds = new HashSet<>();

        for (DriverDistanceInfo driverInfo : nearbyDrivers) {
            DriverLocationDTO driver = driverInfo.getDriver();
            if (rejectedDriverIds.contains(driver.getDriverId())) continue;

            String responseKey = "order-" + dto.getOrderId() + "-driver-" + driver.getDriverId();
            CompletableFuture<Boolean> responseFuture = new CompletableFuture<>();
            driverResponses.put(responseKey, responseFuture);

            Map<String, Object> message = new HashMap<>();
            message.put("orderId", dto.getOrderId());
            message.put("restaurantLat", dto.getRestaurantLatitude());
            message.put("restaurantLng", dto.getRestaurantLongitude());
            message.put("customerLat", dto.getCustomerLatitude());
            message.put("customerLng", dto.getCustomerLongitude());
            message.put("distance", driverInfo.getDistance());
            message.put("eta", driverInfo.getEstimatedMinutes());

            String driverQueue = "/queue/driver/" + driver.getDriverId() + "/orders";
            messagingTemplate.convertAndSend(driverQueue, message);

            try {
                Boolean accepted = responseFuture.get(DRIVER_RESPONSE_TIMEOUT, TimeUnit.SECONDS);
                if (accepted != null && accepted) {
                    AssignDeliveryRequestDTO assignDto = new AssignDeliveryRequestDTO();
                    assignDto.setOrderId(dto.getOrderId());
                    assignDto.setDriverId(driver.getDriverId());
                    assignDto.setRestaurantLatitude(dto.getRestaurantLatitude());
                    assignDto.setRestaurantLongitude(dto.getRestaurantLongitude());
                    assignDto.setCustomerLatitude(dto.getCustomerLatitude());
                    assignDto.setCustomerLongitude(dto.getCustomerLongitude());
                    driverResponses.remove(responseKey);
                    return assignDelivery(assignDto);
                } else {
                    rejectedDriverIds.add(driver.getDriverId());
                }
            } catch (Exception e) {
                rejectedDriverIds.add(driver.getDriverId());
            } finally {
                driverResponses.remove(responseKey);
            }
        }

        throw new RuntimeException("No driver accepted the order");
    }

    @Override
    public DeliveryResponseDTO updateTracking(TrackingUpdateDTO dto) {
        Delivery delivery = deliveryRepo.findByOrderId(dto.getOrderId()).orElseThrow(() -> new RuntimeException("Delivery not found"));

        Location location = new Location();
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setTimestamp(Instant.now());

        delivery.getTracking().add(location);
        deliveryRepo.save(delivery);
        publisher.publishLocation(dto.getOrderId(), location);

        return mapToResponse(delivery);
    }

    @Override
    public List<Location> getTrackingHistory(String orderId) {
        return deliveryRepo.findByOrderId(orderId).map(Delivery::getTracking).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Location> getCurrentLocation(String orderId) {
        return deliveryRepo.findByOrderId(orderId)
                .flatMap(delivery -> {
                    List<Location> list = delivery.getTracking();
                    return list.isEmpty() ? Optional.empty() : Optional.of(list.get(list.size() - 1));
                });
    }

    @Override
    public void updateStatus(String orderId, String status) {
        deliveryRepo.findByOrderId(orderId).ifPresent(delivery -> {
            delivery.setStatus(status.toUpperCase());
            deliveryRepo.save(delivery);
        });
    }

    @Override
    public void completeDelivery(String orderId) {
        deliveryRepo.findByOrderId(orderId).ifPresent(delivery -> {
            delivery.setStatus("DELIVERED");
            delivery.setDeliveredAt(Instant.now());
            deliveryRepo.save(delivery);
        });
    }

    @Override
    public void handleDriverResponse(DriverResponseDTO response) {
        String key = "order-" + response.getOrderId() + "-driver-" + response.getDriverId();
        CompletableFuture<Boolean> future = driverResponses.get(key);
        if (future != null) future.complete(response.isAccepted());
    }

    private DeliveryResponseDTO mapToResponse(Delivery delivery) {
        DeliveryResponseDTO dto = new DeliveryResponseDTO();
        dto.setId(delivery.getId());
        dto.setOrderId(delivery.getOrderId());
        dto.setDriverId(delivery.getDriverId());
        dto.setStatus(delivery.getStatus());
        dto.setRestaurantLatitude(delivery.getRestaurantLatitude());
        dto.setRestaurantLongitude(delivery.getRestaurantLongitude());
        dto.setCustomerLatitude(delivery.getCustomerLatitude());
        dto.setCustomerLongitude(delivery.getCustomerLongitude());
        dto.setAssignedAt(delivery.getAssignedAt());
        dto.setDeliveredAt(delivery.getDeliveredAt());
        dto.setTracking(delivery.getTracking());
        return dto;
    }
}
