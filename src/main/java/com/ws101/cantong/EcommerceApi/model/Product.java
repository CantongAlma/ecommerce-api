package com.ws101.cantong.EcommerceApi.model;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
public class Product {
    
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    private String description;

    @Positive(message = "Price must be a positive number")
    private double price;

    @NotBlank(message = "Category is required")
    private String category;

    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private int stockQuantity;

    private String imageUrl;

    public Product() {}

    public Product(Long id, String name, String description, double price, String category, int stockQuantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}