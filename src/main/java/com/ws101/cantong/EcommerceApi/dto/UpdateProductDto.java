package com.ws101.cantong.EcommerceApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateProductDto {
    
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @Positive(message = "Price must be greater than 0")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private BigDecimal price;
    
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;
    
    private String imageUrl;
    
    private String category;
}