package com.ws101.cantong.EcommerceApi.service;

import com.ws101.cantong.EcommerceApi.model.Product;
import com.ws101.cantong.EcommerceApi.model.Category;
import com.ws101.cantong.EcommerceApi.repository.ProductRepository;
import com.ws101.cantong.EcommerceApi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class for product-related operations.
 * 
 * Provides business logic for creating, retrieving, updating, deleting, 
 * filtering and searching products. This class acts as an intermediary 
 * between the API controller and the data repository layer.
 * 
 * Data is persisted to the database using Spring Data JPA repositories.
 * 
 * Task 2 Number 4 Requirements:
 * - Implement repository pattern for data access
 * - Use Dependency Injection for repositories
 * - Provide CRUD operations
 * - Implement custom query methods for filtering
 * - Add transaction management
 * - Handle exceptions appropriately
 * 
 * @author Alma Cantong
 * @see Product
 * @see ProductRepository
 * @see CategoryRepository
 */
@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    
    /**
     * Creates and saves a new product to the database.
     * If category is provided, it will be associated with the product.
     * 
     * @param product The product details to be created
     * @return The saved product including its generated unique ID
     * @throws RuntimeException if the product is null or invalid
     */
    public Product createProduct(Product product) {
        // Validate category if provided
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + product.getCategory().getId()));
            product.setCategory(category);
        }
        return productRepository.save(product);
    }
    
    /**
     * Retrieves all existing products from the database.
     * 
     * @return List containing all available products. Returns empty list if no products exist.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Finds a product using its unique ID.
     * 
     * @param id The unique identifier of the product to find
     * @return Product object if found
     * @throws RuntimeException if no product matches the given ID
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    /**
     * Updates information of an existing product.
     * Retains the original product ID while updating all other details.
     * 
     * @param id The ID of the product to update
     * @param updatedProduct Object containing new product data
     * @return Updated product object
     * @throws RuntimeException if product with given ID does not exist
     */
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        
        // Update fields
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());
        
        // Update category if provided
        if (updatedProduct.getCategory() != null && updatedProduct.getCategory().getId() != null) {
            Category category = categoryRepository.findById(updatedProduct.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + updatedProduct.getCategory().getId()));
            existingProduct.setCategory(category);
        }
        
        return productRepository.save(existingProduct);
    }
    
    /**
     * Deletes a product from the database using its ID.
     * 
     * @param id The ID of the product to remove
     * @throws RuntimeException if product with given ID is not found
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    /**
     * Filters products by matching category name.
     * Search is case-insensitive.
     * 
     * @param category The category name to use as filter criteria
     * @return List of products belonging to the specified category.
     * @throws RuntimeException if category does not exist
     */
    public List<Product> filterByCategory(String category) {
        Category foundCategory = categoryRepository.findByNameIgnoreCase(category)
            .orElseThrow(() -> new RuntimeException("Category not found: " + category));
        
        return productRepository.findByCategoryId(foundCategory.getId());
    }
    
    /**
     * Filters products within the given price range.
     * Includes products with price equal to minimum or maximum value.
     * 
     * @param minPrice The minimum price limit, must be non-negative
     * @param maxPrice The maximum price limit, must be greater than or equal to minPrice
     * @return List of products whose price falls between the specified range
     * @throws IllegalArgumentException if minPrice is negative, maxPrice is negative,
     * or minPrice value is higher than maxPrice value
     */
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new IllegalArgumentException("Price values are invalid. Min and Max must be non-negative and min <= max.");
        }
        return productRepository.findByPriceBetween(
            BigDecimal.valueOf(minPrice), 
            BigDecimal.valueOf(maxPrice)
        );
    }
    
    /**
     * Searches products by matching keyword in product name.
     * Supports partial text matching and is case-insensitive.
     * 
     * @param keyword The text to search within product names
     * @return List of products whose name contains the given keyword
     */
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
    

    
    /**
     * Gets products with low stock below the specified threshold.
     * Useful for inventory management.
     * 
     * @param threshold The stock quantity threshold
     * @return List of products with stock quantity less than threshold
     */
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findByStockQuantityLessThan(threshold);
    }
    
    /**
     * Gets products by category ID.
     * 
     * @param categoryId The category ID to filter by
     * @return List of products in the specified category
     */
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    /**
     * Checks if a product exists with the given name.
     * 
     * @param name The product name to check
     * @return true if product exists, false otherwise
     */
    public boolean productExistsByName(String name) {
        return productRepository.existsByName(name);
    }
    
    /**
     * Finds products by price greater than or equal to the specified amount.
     * 
     * @param minPrice The minimum price
     * @return List of products with price >= minPrice
     */
    public List<Product> getProductsByMinPrice(double minPrice) {
        return productRepository.findByPriceGreaterThanEqual(BigDecimal.valueOf(minPrice));
    }
    
    /**
     * Finds products by price less than or equal to the specified amount.
     * 
     * @param maxPrice The maximum price
     * @return List of products with price <= maxPrice
     */
    public List<Product> getProductsByMaxPrice(double maxPrice) {
        return productRepository.findByPriceLessThanEqual(BigDecimal.valueOf(maxPrice));
    }
    
    /**
     * Gets the count of total products.
     * 
     * @return The total number of products
     */
    public long getProductCount() {
        return productRepository.count();
    }
    
    /**
     * Updates stock quantity for a product.
     * 
     * @param id The product ID
     * @param newStockQuantity The new stock quantity
     * @return The updated product
     */
    public Product updateStockQuantity(Long id, Integer newStockQuantity) {
        Product product = getProductById(id);
        product.setStockQuantity(newStockQuantity);
        return productRepository.save(product);
    }
    
    /**
     * Reduces stock quantity when a product is purchased.
     * 
     * @param id The product ID
     * @param quantity The quantity to deduct
     * @return The updated product
     * @throws RuntimeException if insufficient stock
     */
    public Product reduceStock(Long id, Integer quantity) {
        Product product = getProductById(id);
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        return productRepository.save(product);
    }
    

    
    /**
     * Creates multiple products at once.
     * 
     * @param products List of products to create
     * @return List of saved products
     */
    public List<Product> createMultipleProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }
    
    /**
     * Deletes all products (use with caution).
     */
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
    
    
    
    /**
     * Finds a product by name (exact match, case-insensitive).
     * 
     * @param name The product name to search for
     * @return Optional containing the product if found
     */
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByNameIgnoreCase(name);
    }
}