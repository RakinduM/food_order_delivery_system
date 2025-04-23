package com.edu.orderservice.service;

import com.edu.orderservice.dto.OrderRequestDTO;
import com.edu.orderservice.dto.OrderResponseDTO;
import com.edu.orderservice.document.Order;
import com.edu.orderservice.document.OrderItem;
import com.edu.orderservice.document.OrderStatus;
import com.edu.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {

        List<OrderItem> items = dto.getItems().stream().map(i ->
                modelMapper.map(i, OrderItem.class)).collect(Collectors.toList());

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        Order order = new Order();
        order.setCustomerId(dto.getCustomerId());
        order.setRestaurantId(dto.getRestaurantId());
        order.setCustomer_latitude(dto.getCustomer_latitude());
        order.setCustomer_longitude(dto.getCustomer_longitude());
        order.setRestaurant_latitude(dto.getRestaurant_latitude());
        order.setRestaurant_longitude(dto.getRestaurant_longitude());
        order.setItems(items);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        Order saved = orderRepository.save(order);

        return modelMapper.map(saved, OrderResponseDTO.class);
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<OrderResponseDTO> getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .map(order -> modelMapper.map(order,
                        OrderResponseDTO.class));
    }

    public Optional<OrderResponseDTO> updateOrderStatus(String orderId, OrderStatus status) {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setStatus(status);
            order.setUpdatedAt(Instant.now());
            Order updated = orderRepository.save(order);
            return Optional.of(modelMapper.map(updated, OrderResponseDTO.class));
        }
        return Optional.empty();
    }

}
