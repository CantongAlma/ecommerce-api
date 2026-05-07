package com.ws101.cantong.EcommerceApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductDto {
    
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "99999.99", message = "Price cannot exceed 99999.99")
    private BigDecimal price;
    
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    @Max(value = 10000, message = "Stock quantity cannot exceed 10000")
    private Integer stockQuantity;
    
    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String imageUrl;
    
    private String category;
}