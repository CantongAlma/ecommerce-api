package com.ws101.cantong.EcommerceApi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.ws101.cantong.EcommerceApi.entity.User;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
public class TestSecurityController {

    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a public endpoint - anyone can access");
        return response;
    }

   
    @GetMapping("/authenticated")
    @PreAuthorize("isAuthenticated()")
    public Map<String, String> authenticatedEndpoint(@AuthenticationPrincipal User user) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "You are authenticated!");
        response.put("user", user.getEmail());
        return response;
    }

    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> adminEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Only ADMIN can see this!");
        return response;
    }

    
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public Map<String, String> userEndpoint(@AuthenticationPrincipal User user) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Only USER role can see this!");
        response.put("user", user.getEmail());
        return response;
    }

    
    @GetMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public Map<String, String> sellerEndpoint(@AuthenticationPrincipal User user) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Only SELLER role can see this!");
        response.put("user", user.getEmail());
        return response;
    }
}