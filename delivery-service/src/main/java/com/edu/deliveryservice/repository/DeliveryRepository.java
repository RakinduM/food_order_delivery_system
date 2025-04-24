package com.edu.deliveryservice.repository;

import com.edu.deliveryservice.model.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    Optional<Delivery> findByOrderId(String orderId);
}
