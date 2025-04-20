package com.edu.orderservice.service;

import com.edu.orderservice.dto.OrderItemRequestDTO;
import com.edu.orderservice.dto.OrderRequestDTO;
import com.edu.orderservice.dto.OrderResponseDTO;
import com.edu.orderservice.document.Order;
import com.edu.orderservice.document.OrderItem;
import com.edu.orderservice.document.OrderStatus;
import com.edu.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {

        List<OrderItem> items = dto.getItems().stream().map(i ->
                new OrderItem(i.getMenuItemId(), i.getName(), i.getQuantity(), i.getPrice())
        ).toList();

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        Order order = new Order();
        order.setCustomerId(dto.getCustomerId());
        order.setRestaurantId(dto.getRestaurantId());
        order.setItems(items);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        Order saved = orderRepository.save(order);

        return mapToResponseDTO(saved);
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderResponseDTO> getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .map(this::mapToResponseDTO);
    }

    public Optional<OrderResponseDTO> updateOrderStatus(String orderId, OrderStatus status) {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setStatus(status);
            order.setUpdatedAt(Instant.now());
            return Optional.of(mapToResponseDTO(orderRepository.save(order)));
        }
        return Optional.empty();
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        List<OrderItemRequestDTO> items = order.getItems().stream().map(i ->
                new OrderItemRequestDTO(i.getMenuItemId(), i.getName(), i.getQuantity(), i.getPrice())
        ).collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getTotalAmount(),
                order.getStatus(),
                items,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }


}
