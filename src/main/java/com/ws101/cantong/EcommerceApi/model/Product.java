package com.ws101.cantong.EcommerceApi.model;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Model class representing a Product entity.
 * 
 * Defines the structure and attributes of product data.
 * Includes validation rules to ensure data integrity and correctness.
 * 
 * @author Alma Cantong
 */
public class Product {
    /** Unique identification number assigned to each product */
    private Long id;

    /** 
     * Name of the product
     * Required field, must contain at least 2 characters
     */
    @NotBlank(message = "Product name is required")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    /** Short description or details about the product */
    private String description;

    /** 
     * Selling price of the product
     * Must be a positive value greater than 0
     */
    @Positive(message = "Price must be a positive number")
    private double price;

    /** 
     * Category group where product belongs
     * Required field, cannot be empty
     */
    @NotBlank(message = "Category is required")
    private String category;

    /** 
     * Quantity of items available in stock
     * Can be zero or any positive number, negative value is not allowed
     */
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private int stockQuantity;

    /** File path or URL link to product display image */
    private String imageUrl;

    /**
     * Default constructor required for object initialization
     */
    public Product() {}

    /**
     * Parameterized constructor to create complete product object
     * 
     * @param id Unique identifier of the product
     * @param name Name of the product
     * @param description Brief details about the product
}

    
    