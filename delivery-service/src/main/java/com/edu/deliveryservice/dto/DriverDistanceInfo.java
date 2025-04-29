package com.edu.deliveryservice.dto;

public class DriverDistanceInfo {
    private DriverLocationDTO driver;
    private double distance;
    private double estimatedMinutes;

    public DriverDistanceInfo(DriverLocationDTO driver, double distance, double estimatedMinutes) {
        this.driver = driver;
        this.distance = distance;
        this.estimatedMinutes = estimatedMinutes;
    }

    public DriverLocationDTO getDriver() {
        return driver;
    }

    public double getDistance() {
        return distance;
    }

    public double getEstimatedMinutes() {
        return estimatedMinutes;
    }
}
