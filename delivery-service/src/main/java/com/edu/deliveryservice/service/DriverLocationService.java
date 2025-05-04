package com.edu.deliveryservice.service;

import com.edu.deliveryservice.dto.DriverLocationDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DriverLocationService {

    private final Map<String, DriverLocationDTO> liveDriverLocations = new ConcurrentHashMap<>();

    public void updateDriverLocation(String driverId, DriverLocationDTO location) {
        liveDriverLocations.put(driverId, location);
    }

    public DriverLocationDTO getLocation(String driverId) {
        return liveDriverLocations.get(driverId);
    }

    public List<DriverLocationDTO> getAllDriverLocations() {
        return new ArrayList<>(liveDriverLocations.values());
    }

    public void removeDriver(String driverId) {
        liveDriverLocations.remove(driverId);
    }
}
