package com.yourpackage.model;
 import lombok.AllArgsConstructor;
 import lombok.Getter;
 import lombok.NoArgsConstructor;
 import lombok.Setter;
 import lombok.ToString;
 @Getter
 @Setter
 @NoArgsConstructor   // Default constructor required by Spring
 @AllArgsConstructor   // Constructor with all fields
 @ToString
 public class Product {
     // Fields
     private Long id;
     private String name;
     private String description;
     private double price;
     private String category;
     private int stockQuantity;
     private String imageUrl; // Optional
 }