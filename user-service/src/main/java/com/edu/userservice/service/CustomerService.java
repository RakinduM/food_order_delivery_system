package com.edu.userservice.service;

import com.edu.userservice.dto.AuthResponse;
import com.edu.userservice.dto.CustomerRes;
import com.edu.userservice.dto.CustomerRegRequest;

public interface CustomerService {
    AuthResponse register(CustomerRegRequest request);
    AuthResponse authenticate(String username, String password);
    CustomerRes getUserByUsername(String username);
    //AuthResponse login(AuthRequest authRequest);
//    List<Customer> getAll();
//    Customer getById(String id);
//    Customer update(String id, Customer updatedCustomer);
//    void delete(String id);
}
