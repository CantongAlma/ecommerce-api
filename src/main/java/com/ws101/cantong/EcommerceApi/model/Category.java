package com.ws101.cantong.EcommerceApi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Product Category in the e-commerce system.
 * Categories help organize products into logical groups for easier browsing and filtering.
 * 
 * <p>Relationships:</p>
 * <ul>
 *   <li><b>One-to-Many</b> with {@link Product} - One category can contain multiple products</li>
 * </ul>
 * 
 * <p>This entity is the inverse side of the relationship. The owning side is
 * {@link Product#getCategory()}.</p>
 * 
 * @author Your Name
 * @version 1.0
 * @see Product
 */
@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String description;
    
    /**
     * One-to-Many relationship with Product.
     * One category can have multiple products associated with it.
     * 
     * <p>The 'mappedBy' attribute indicates this is the inverse side of the relationship,
     * with the owning side being the 'category' field in the Product entity.</p>
     * 
     * <p>CascadeType.ALL means all JPA operations (persist, merge, remove, refresh, detach)
     * performed on a Category will cascade to all its associated Products.</p>
     * 
     * <p>Fetch type LAZY means the collection of products is loaded only when explicitly accessed,
     * improving performance by avoiding unnecessary database queries.</p>
     * 
     * <p>@JsonIgnore prevents infinite recursion during JSON serialization when
     * bidirectional relationships exist between Category and Product.</p>
     * 
     * @see Product
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Prevents infinite recursion in JSON serialization
    private List<Product> products = new ArrayList<>();
    
    /**
     * Default constructor required by JPA.
     */
    public Category() {
    }
    
    /**
     * Constructor with essential category information.
     * 
     * @param name the category name (must be unique)
     * @param description the category description
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    /**
     * Helper method to maintain the bidirectional relationship consistency.
     * Adds a product to this category and sets the product's category reference to this category.
     * 
     * <p>This method ensures both sides of the relationship are synchronized,
     * preventing orphaned references and maintaining data integrity.</p>
     * 
     * @param product the product to add to this category
     * @see #removeProduct(Product)
     */
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }
    
    /**
     * Convenience method to get the category name as a String.
     * This method is useful for filtering and comparison operations.
     * 
     * @return the category name
     * @see #getName()
     */
    public String getCategoryName() {
        return this.name;  // Returns the 'name' field
    }
    
    /**
     * Helper method to maintain the bidirectional relationship consistency.
     * Removes a product from this category and clears the product's category reference.
     * 
     * <p>This method ensures both sides of the relationship are synchronized,
     * properly breaking the association between the category and product.</p>
     * 
     * @param product the product to remove from this category
     * @see #addProduct(Product)
     */
    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }
    
    /**
     * Gets the category ID.
     * 
     * @return the category ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the category ID.
     * 
     * @param id the category ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the category name.
     * 
     * @return the category name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the category name.
     * Category names must be unique across all categories.
     * 
     * @param name the category name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the category description.
     * 
     * @return the category description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the category description.
     * 
     * @param description the category description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the list of products belonging to this category.
     * 
     * @return list of products in this category
     * @see Product
     */
    public List<Product> getProducts() {
        return products;
    }
    
    /**
     * Sets the list of products belonging to this category.
     * 
     * <p>Note: It's recommended to use the {@link #addProduct(Product)} and
     * {@link #removeProduct(Product)} helper methods to maintain
     * bidirectional relationship consistency instead of setting the list directly.</p>
     * 
     * @param products the list of products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}