package com.edu.deliveryservice.config;

import com.edu.deliveryservice.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TrackingWebSocketPublisher {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void publishLocation(String orderId, Location location) {
        messagingTemplate.convertAndSend("/topic/delivery/" + orderId, location);
    }
}
