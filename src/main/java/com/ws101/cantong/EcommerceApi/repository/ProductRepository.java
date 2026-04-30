package com.ws101.cantong.EcommerceApi.repository;

import com.ws101.cantong.EcommerceApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product entity operations.
 * Provides CRUD operations and custom query methods for Product management.
 * 
 * @author Alma Cantong
 * @version 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Finds all products belonging to a specific category.
     * 
     * @param categoryId the ID of the category
     * @return list of products in the specified category
     */
    List<Product> findByCategoryId(Long categoryId);
    
    /**
     * Finds all products within a price range.
     * 
     * @param min the minimum price (inclusive)
     * @param max the maximum price (inclusive)
     * @return list of products within the price range
     */
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
    
    /**
     * Finds products by name containing the given keyword (case-insensitive).
     * 
     * @param name the keyword to search for in product names
     * @return list of products with matching names
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Finds products with stock quantity below the specified threshold.
     * Useful for inventory management and reordering alerts.
     * 
     * @param threshold the stock quantity threshold
     * @return list of products with low stock
     */
    List<Product> findByStockQuantityLessThan(Integer threshold);
    
    /**
     * Checks if a product exists with the given name.
     * 
     * @param name the product name to check
     * @return true if product exists, false otherwise
     */
    boolean existsByName(String name);
    
    
    // ==================== ADD THESE MISSING METHODS ====================
    /**
     * Finds products with price greater than or equal to the specified amount.
     * 
     * @param minPrice the minimum price (inclusive)
     * @return list of products with price >= minPrice
     */
    List<Product> findByPriceGreaterThanEqual(BigDecimal minPrice);
    
    /**
     * Finds products with price less than or equal to the specified amount.
     * 
     * @param maxPrice the maximum price (inclusive)
     * @return list of products with price <= maxPrice
     */
    List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);
    
    /**
     * Finds a product by name (exact match, case-insensitive).
     * 
     * @param name the product name to search for
     * @return Optional containing the product if found
     */
    Optional<Product> findByNameIgnoreCase(String name);
    
    // ==================== Additional Useful Methods ====================
    
    /**
     * Finds products with price greater than the specified amount.
     * 
     * @param minPrice the minimum price (exclusive)
     * @return list of products with price > minPrice
     */
    List<Product> findByPriceGreaterThan(BigDecimal minPrice);
    
    /**
     * Finds products with price less than the specified amount.
     * 
     * @param maxPrice the maximum price (exclusive)
     * @return list of products with price < maxPrice
     */
    List<Product> findByPriceLessThan(BigDecimal maxPrice);
    
    /**
     * Finds products by category ID and price between range.
     * 
     * @param categoryId the category ID
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of products matching both criteria
     */
    List<Product> findByCategoryIdAndPriceBetween(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Counts products in a specific category.
     * 
     * @param categoryId the category ID
     * @return number of products in the category
     */
    long countByCategoryId(Long categoryId);
    
    /**
     * Finds products with stock quantity greater than the specified threshold.
     * 
     * @param threshold the stock quantity threshold
     * @return list of products with sufficient stock
     */
    List<Product> findByStockQuantityGreaterThan(Integer threshold);
}