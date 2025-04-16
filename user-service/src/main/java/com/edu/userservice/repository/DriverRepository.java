package com.edu.userservice.repository;

import com.edu.userservice.model.Customer;
import com.edu.userservice.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {
    Optional<Driver> findByUsername(String username);
    boolean existsByUsername(String username);
}
