package com.edu.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Document(collection = "restaurents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant implements UserDetails {
    @Id
    private String id;
    private String restaurantName;
    private String restaurantAdmin;
    private String type;
    private String address;
    private String email;
    private String phoneNumber;
    private String businessDoc;
    private Boolean isAvailable;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For simplicity, return a default role
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
