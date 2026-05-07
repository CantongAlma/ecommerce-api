package com.ws101.cantong.EcommerceApi.controller;

import com.ws101.cantong.EcommerceApi.model.Product;
import com.ws101.cantong.EcommerceApi.service.ProductService;
import com.ws101.cantong.EcommerceApi.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing Product operations.
 * Provides endpoints for CRUD operations, filtering, and searching products.
 * 
 * @author Alma Cantong
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/products")  // CHANGED: from "/api/products" to "/api/v1/products"
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor injection of ProductService.
     * 
     * @param productService the product service to be injected
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products.
     * 
     * @return ResponseEntity containing list of all products with HTTP 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id the product ID
     * @return ResponseEntity containing the product with HTTP 200 OK status
     * @throws ProductNotFoundException if product with given ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Filters products based on filter type and value.
     * Supported filter types: category, name, price
     * 
     * @param filterType the type of filter (category, name, or price)
     * @param filterValue the value to filter by
     * @return ResponseEntity containing filtered list of products with HTTP 200 OK status
     * @throws IllegalArgumentException if filter type is invalid or price format is incorrect
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue) {

        List<Product> result;

        switch (filterType.toLowerCase()) {
            case "category":
                result = productService.filterByCategory(filterValue);
                break;
            case "name":
                result = productService.searchByName(filterValue);
                break;
            case "price":
                String[] range = filterValue.split(",");
                if (range.length != 2) {
                    throw new IllegalArgumentException("Invalid price format. Use: min,max (e.g. 100,500)");
                }
                double min = Double.parseDouble(range[0]);
                double max = Double.parseDouble(range[1]);
                result = productService.filterByPriceRange(min, max);
                break;
            default:
                throw new IllegalArgumentException("Invalid filter type: " + filterType + ". Use: category, name, or price");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Creates a new product.
     * 
     * @param product the product to create
     * @return ResponseEntity containing the created product with HTTP 201 CREATED status
     * @throws IllegalArgumentException if product name is empty
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Updates an existing product completely.
     * 
     * @param id the ID of the product to update
     * @param product the updated product data
     * @return ResponseEntity containing the updated product with HTTP 200 OK status
     * @throws ProductNotFoundException if product with given ID is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        Product updated = productService.updateProduct(id, product);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Partially updates an existing product.
     * Only updates fields that are provided in the request body.
     * 
     * @param id the ID of the product to update
     * @param updates map containing the fields to update and their new values
     * @return ResponseEntity containing the updated product with HTTP 200 OK status
     * @throws ProductNotFoundException if product with given ID is not found
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
            
        Product existing = productService.getProductById(id);

        // Apply partial updates
        if (updates.containsKey("name")) {
            existing.setName((String) updates.get("name"));
        }
        if (updates.containsKey("description")) {
            existing.setDescription((String) updates.get("description"));
        }
        if (updates.containsKey("price")) {
            Object priceValue = updates.get("price");
            if (priceValue instanceof Double) {
                existing.setPrice(BigDecimal.valueOf((Double) priceValue));
            } else if (priceValue instanceof Integer) {
                existing.setPrice(BigDecimal.valueOf((Integer) priceValue));
            } else if (priceValue instanceof String) {
                existing.setPrice(new BigDecimal((String) priceValue));
            }
        }
        if (updates.containsKey("stockQuantity")) {
            existing.setStockQuantity((Integer) updates.get("stockQuantity"));
        }
        if (updates.containsKey("imageUrl")) {
            existing.setImageUrl((String) updates.get("imageUrl"));
        }

        Product saved = productService.updateProduct(id, existing);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id the ID of the product to delete
     * @return ResponseEntity with HTTP 204 NO CONTENT status
     * @throws ProductNotFoundException if product with given ID is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * Gets products with low stock below the specified threshold.
     * 
     * @param threshold the stock threshold (default is 10)
     * @return ResponseEntity containing list of low stock products
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts(
            @RequestParam(defaultValue = "10") int threshold) {
        List<Product> products = productService.getLowStockProducts(threshold);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    /**
     * Gets products by category ID.
     * 
     * @param categoryId the category ID to filter by
     * @return ResponseEntity containing list of products in the category
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}