package com.edu.userservice.service;

import com.edu.userservice.dto.AuthRequest;
import com.edu.userservice.dto.AuthResponse;
import com.edu.userservice.dto.CustomerRes;
import com.edu.userservice.dto.RegRequest;
import com.edu.userservice.model.Customer;

import java.util.List;

public interface CustomerService {
    AuthResponse register(RegRequest request);
    AuthResponse authenticate(String username, String password);
    CustomerRes getUserByUsername(String username);
    //AuthResponse login(AuthRequest authRequest);
//    List<Customer> getAll();
//    Customer getById(String id);
//    Customer update(String id, Customer updatedCustomer);
//    void delete(String id);
}
