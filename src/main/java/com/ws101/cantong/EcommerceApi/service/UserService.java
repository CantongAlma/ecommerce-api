package com.ws101.cantong.EcommerceApi.service;

import com.ws101.cantong.EcommerceApi.entity.Role;
import com.ws101.cantong.EcommerceApi.entity.User;
import com.ws101.cantong.EcommerceApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user with validation and password encoding
     */
    @Transactional
    public User registerUser(String email, String password, String firstName, String lastName, String role) {
       
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already registered: " + email);
        }
        
       
        if (password == null || password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters long");
        }
        
    
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new RuntimeException("First name is required");
        }
        
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new RuntimeException("Last name is required");
        }
        
    
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Hash the password
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
        
        Role userRole;
        try {
            if (role != null && !role.isEmpty()) {
                userRole = Role.valueOf(role.toUpperCase());
            } else {
                userRole = Role.USER;
            }
        } catch (IllegalArgumentException e) {
            userRole = Role.USER; // Default to USER role
        }
        
        user.setRoles(Set.of(userRole));
        
       
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        
        return userRepository.save(user);
    }

    /**
     * Find user by email
     */
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    /**
     * Check if email exists
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}