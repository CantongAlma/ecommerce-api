package com.ws101.cantong.EcommerceApi.service;

import com.ws101.cantong.EcommerceApi.exception.ResourceNotFoundException;
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

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * Creates and saves a new product to the database.
     */
    public Product createProduct(Product product) {
        // Validate category if provided
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", product.getCategory().getId()));
            product.setCategory(category);
        }
        return productRepository.save(product);
    }
    
    /**
     * Retrieves all existing products from the database.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Finds a product using its unique ID.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }
    
    /**
     * Updates information of an existing product.
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
                .orElseThrow(() -> new ResourceNotFoundException("Category", updatedProduct.getCategory().getId()));
            existingProduct.setCategory(category);
        }
        
        return productRepository.save(existingProduct);
    }
    
    /**
     * Deletes a product from the database using its ID.
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }
    
    /**
     * Filters products by matching category name.
     */
    public List<Product> filterByCategory(String category) {
        Category foundCategory = categoryRepository.findByNameIgnoreCase(category)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "name", category));
        
        return productRepository.findByCategoryId(foundCategory.getId());
    }
    
    /**
     * Filters products within the given price range.
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
     */
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    /**
     * Gets products with low stock below the specified threshold.
     */
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findByStockQuantityLessThan(threshold);
    }
    
    /**
     * Gets products by category ID.
     */
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    /**
     * Checks if a product exists with the given name.
     */
    public boolean productExistsByName(String name) {
        return productRepository.existsByName(name);
    }
    
    /**
     * Finds products by price greater than or equal to the specified amount.
     */
    public List<Product> getProductsByMinPrice(double minPrice) {
        return productRepository.findByPriceGreaterThanEqual(BigDecimal.valueOf(minPrice));
    }
    
    /**
     * Finds products by price less than or equal to the specified amount.
     */
    public List<Product> getProductsByMaxPrice(double maxPrice) {
        return productRepository.findByPriceLessThanEqual(BigDecimal.valueOf(maxPrice));
    }
    
    /**
     * Gets the count of total products.
     */
    public long getProductCount() {
        return productRepository.count();
    }
    
    /**
     * Updates stock quantity for a product.
     */
    public Product updateStockQuantity(Long id, Integer newStockQuantity) {
        Product product = getProductById(id);
        product.setStockQuantity(newStockQuantity);
        return productRepository.save(product);
    }
    
    /**
     * Reduces stock quantity when a product is purchased.
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
     */
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByNameIgnoreCase(name);
    }
}