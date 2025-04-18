package com.edu.userservice.repository;

import com.edu.userservice.model.Admin;
import com.edu.userservice.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}
