package com.edu.deliveryservice.controller;

import com.edu.deliveryservice.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/send")         // Frontend sends to /app/send
    @SendTo("/topic/updates")        // Broadcasts to /topic/updates
    public Message handleMessage(Message message) {
        System.out.println("Received from frontend: " + message.getContent());
        return new Message("Server response: " + message.getContent());
    }
}
