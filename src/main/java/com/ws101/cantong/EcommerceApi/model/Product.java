package com.ws101.cantong.EcommerceApi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Product in the e-commerce system.
 * A product represents an item that can be purchased by customers.
 * 
 * <p>Relationships:</p>
 * <ul>
 *   <li><b>Many-to-One</b> with {@link Category} - Each product belongs to exactly one category</li>
 *   <li><b>One-to-Many</b> with {@link OrderItem} - Each product can appear in multiple order items</li>
 * </ul>
 * 
 * <p>This entity is the owning side of both relationships.</p>
 * 
 * @author Your Name
 * @version 1.0
 * @see Category
 * @see OrderItem
 */
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    private String imageUrl;
    
    /**
     * Many-to-One relationship with Category.
     * Multiple products can belong to one category.
     * 
     * <p>Fetch type LAZY means the category is loaded only when accessed.
     * The foreign key column is 'category_id' in the products table.</p>
     * 
     * <p>This is the owning side of the relationship.</p>
     * 
     * @see Category
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    
    /**
     * One-to-Many relationship with Order through OrderItem.
     * One product can be part of multiple order items (different orders or same order).
     * 
     * <p>CascadeType.ALL means operations (persist, merge, remove, refresh, detach) 
     * on Product cascade to associated OrderItem entities.</p>
     * 
     * <p>'mappedBy' indicates this is the inverse side of the relationship.
     * The owning side is OrderItem.product.</p>
     * 
     * @see OrderItem
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();
    
    /**
     * Default constructor required by JPA.
     */
    public Product() {
    }
    
    /**
     * Constructor with essential product information.
     * 
     * @param name the product name
     * @param description the product description
     * @param price the product price
     * @param stockQuantity the available stock quantity
     */
    public Product(String name, String description, BigDecimal price, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    /**
     * Gets the product ID.
     * 
     * @return the product ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the product ID.
     * 
     * @param id the product ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the product name.
     * 
     * @return the product name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the product name.
     * 
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the product description.
     * 
     * @return the product description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the product description.
     * 
     * @param description the product description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the product price.
     * 
     * @return the product price as BigDecimal for precision
     */
    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * Sets the product price.
     * 
     * @param price the product price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    /**
     * Gets the available stock quantity.
     * 
     * @return the stock quantity
     */
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    /**
     * Sets the available stock quantity.
     * 
     * @param stockQuantity the stock quantity to set
     */
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    /**
     * Gets the product image URL.
     * 
     * @return the image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * Sets the product image URL.
     * 
     * @param imageUrl the image URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * Gets the category this product belongs to.
     * 
     * @return the associated category
     * @see Category
     */
    public Category getCategory() {
        return category;
    }
    
    /**
     * Sets the category this product belongs to.
     * 
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    
    /**
     * Gets the list of order items containing this product.
     * 
     * @return list of order items
     * @see OrderItem
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    /**
     * Sets the list of order items containing this product.
     * 
     * @param orderItems the list of order items to set
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}