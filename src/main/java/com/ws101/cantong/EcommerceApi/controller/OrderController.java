package com.ws101.cantong.EcommerceApi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.ws101.cantong.EcommerceApi.entity.User;

import java.util.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

   
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(@AuthenticationPrincipal User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Orders list - Only authenticated users can see this");
        response.put("user", user.getEmail());
        response.put("orders", Arrays.asList("Order 1", "Order 2", "Order 3"));
        return ResponseEntity.ok(response);
    }

    
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order created successfully - Only USER role can create orders");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order details for order ID: " + id);
        response.put("user", user.getEmail());
        response.put("orderId", id);
        return ResponseEntity.ok(response);
    }

   
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Map<String, String>> getAllOrders() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "All orders - Only ADMIN can view this");
        return ResponseEntity.ok(response);
    }

   
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order " + id + " status updated to: " + status);
        response.put("status", status);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}