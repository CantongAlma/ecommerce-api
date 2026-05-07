package com.ws101.cantong.EcommerceApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class OrderDto {
    
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;
    
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemDto> items;
    
    @Size(max = 500, message = "Shipping address cannot exceed 500 characters")
    private String shippingAddress;
    
    @Pattern(regexp = "PENDING|PROCESSING|SHIPPED|DELIVERED|CANCELLED", 
             message = "Status must be PENDING, PROCESSING, SHIPPED, DELIVERED, or CANCELLED")
    private String status = "PENDING";
}

@Data
class OrderItemDto {
    
    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    private Long productId;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    @Max(value = 999, message = "Quantity cannot exceed 999")
    private Integer quantity;
    
    @Positive(message = "Price must be greater than 0")
    private Double price;
}