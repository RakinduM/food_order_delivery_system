package com.edu.orderservice.controller;

import com.edu.orderservice.dto.OrderRequestDTO;
import com.edu.orderservice.dto.OrderResponseDTO;
import com.edu.orderservice.model.OrderStatus;
import com.edu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO dto){
        return ResponseEntity.ok(orderService.placeOrder(dto));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(@PathVariable String customerId){
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity <OrderResponseDTO> getOrder(@PathVariable String orderId) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(orderId, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
