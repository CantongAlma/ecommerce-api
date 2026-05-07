package com.ws101.cantong.EcommerceApi.controller;

import com.ws101.cantong.EcommerceApi.dto.RegisterUserDto;
import com.ws101.cantong.EcommerceApi.entity.User;
import com.ws101.cantong.EcommerceApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto registrationDto) {
        try {
            User registeredUser = userService.registerUser(
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getRole()
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully!");
            response.put("userId", registeredUser.getId());
            response.put("email", registeredUser.getEmail());
            response.put("firstName", registeredUser.getFirstName());
            response.put("lastName", registeredUser.getLastName());
            response.put("roles", registeredUser.getRoles());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("available", !exists);
        return ResponseEntity.ok(response);
    }
}