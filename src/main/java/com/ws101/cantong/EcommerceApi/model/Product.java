package com.ws101.cantong.EcommerceApi.model;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@NotBlank(message = "Product name is required")
private String name;

@NotNull(message = "Price is required")
@Positive(message = "Price must be positive")
private BigDecimal price;

@Min(value = 0, message = "Stock cannot be negative")
private Integer stockQuantity;
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;
    private String imageUrl;
    
    // Default constructor
    public Product() {
    }
    
    // All-args constructor
    public Product(Long id, String name, String description, BigDecimal price, String category, Integer stockQuantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    // toString method
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}