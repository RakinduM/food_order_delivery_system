package com.edu.userservice.service;

import com.edu.userservice.model.Customer;
import com.edu.userservice.repository.AdminRepository;
import com.edu.userservice.repository.CustomerRepository;
import com.edu.userservice.repository.DriverRepository;
import com.edu.userservice.repository.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return userRepository.findByUsername(usernameOrEmail)
                .<UserDetails>map(user -> user)
                .or(() -> driverRepository.findByUsername(usernameOrEmail)
                        .<UserDetails>map(driver -> driver))
                .or(() -> adminRepository.findByUsername(usernameOrEmail)
                        .<UserDetails>map(admin -> admin))
                .or(() -> restaurantRepository.findByEmail(usernameOrEmail)
                        .<UserDetails>map(restaurant -> restaurant))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + usernameOrEmail));
    }
}
