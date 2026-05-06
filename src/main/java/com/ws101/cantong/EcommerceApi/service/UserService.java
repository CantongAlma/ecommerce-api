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
    
    @Transactional
    public User registerUser(String email, String password, String firstName, String lastName, String role) {
        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already registered!");
        }
        
        // Create new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Hash the password
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
        // Assign role (default to USER if invalid role provided)
        try {
            Role userRole = Role.valueOf(role.toUpperCase());
            user.setRoles(Set.of(userRole));
        } catch (IllegalArgumentException e) {
            user.setRoles(Set.of(Role.USER)); // Default to USER role
        }
        
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        
        return userRepository.save(user);
    }
    
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}