package com.ws101.cantong.EcommerceApi.model;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Model class representing a Product entity.
 * Stores all product information and includes validation rules
 * to ensure input data is correct and complete.
 */
public class Product {
    
    /** Unique identification number for each product */
    private Long id;

    /** 
     * Name of the product
     * Required field, must have at least 2 characters
     */
    @NotBlank(message = "Product name is required")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    /** Short details or description about the product */
    private String description;

    /** 
     * Selling price of the product
     * Must be a value greater than 0
     */
    @Positive(message = "Price must be a positive number")
    private double price;

    /** 
     * Category group where the product belongs
     * Required field, cannot be empty
     */
    @NotBlank(message = "Category is required")
    private String category;

    /** 
     * Number of items available in stock
     * Can be 0 or higher, negative value is not allowed
     */
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private int stockQuantity;

    /** URL or file path of the product display image */
    private String imageUrl;

    /**
     * Default empty constructor
     */
    public Product() {}

    /**
     * Constructor to create a complete Product object
     * 
     * @param id Unique ID assigned to product
     * @param name Name of the product
     * @param description Brief details of the product
     * @param price Selling price value
     * @param category Classification group of product
     * @param stockQuantity Available quantity in storage
     * @param imageUrl Location link of product image
     */
    public Product(Long id, String name, String description, double price, String category, int stockQuantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    /**
     * Get the ID of the product
     * @return Product ID number
     */
    public Long getId() { return id; }

    /**
     * Set or update the ID of the product
     * @param id New ID value to assign
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Get the name of the product
     * @return Product name text
     */
    public String getName() { return name; }

    /**
     * Set or update the name of the product
     * @param name New name value
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get the description of the product
     * @return Product description text
     */
    public String getDescription() { return description; }

    /**
     * Set or update the description of the product
     * @param description New description details
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Get the price of the product
     * @return Product price value
     */
    public double getPrice() { return price; }

    /**
     * Set or update the price of the product
     * @param price New price amount
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Get the category of the product
     * @return Category name
     */
    public String getCategory() { return category; }

    /**
     * Set or update the category of the product
     * @param category New category name
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * Get the available stock quantity
     * @return Stock count number
     */
    public int getStockQuantity() { return stockQuantity; }

    /**
     * Set or update the stock quantity
     * @param stockQuantity New available stock number
     */
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    /**
     * Get the image URL of the product
     * @return Image path or link
     */
    public String getImageUrl() { return imageUrl; }

    /**
     * Set or update the image URL of the product
     * @param imageUrl New image location link
     */
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}