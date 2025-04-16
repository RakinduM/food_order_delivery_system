package com.edu.userservice.service;

import com.edu.userservice.model.Customer;
import com.edu.userservice.repository.CustomerRepository;
import com.edu.userservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository userRepository;
    private final DriverRepository driverRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .<UserDetails>map(user -> user)
                .or(() -> driverRepository.findByUsername(username)
                        .<UserDetails>map(driver -> driver))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
