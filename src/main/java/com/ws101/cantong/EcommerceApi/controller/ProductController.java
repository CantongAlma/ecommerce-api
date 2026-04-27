package com.ws101.cantong.EcommerceApi.controller;

import com.ws101.cantong.EcommerceApi.model.Product;
import com.ws101.cantong.EcommerceApi.service.ProductService;
import com.ws101.cantong.EcommerceApi.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for handling all product-related API requests.
 * 
 * Acts as entry point for client requests, manages HTTP requests and responses,
 * and communicates with service layer to process business logic.
 * Provides endpoints for CRUD operations, filtering and partial updates.
 * 
 * @author Alma Cantong
 * @see ProductService
 * @see Product
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor to inject required dependency.
     * 
     * @param productService Service component containing product business logic
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves complete list of all available products.
     * 
     * @return ResponseEntity containing list of all products and HTTP 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Retrieves specific product using its unique identifier.
     * 
     * @param id Unique ID of the product to retrieve
     * @return ResponseEntity containing found product data and HTTP 200 OK status
     * @throws ProductNotFoundException if no product exists with the provided ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Filters products based on selected criteria.
     * Supports filtering by category, name keyword or price range.
     * 
     * @param filterType Type of filter to apply: "category", "name" or "price"
     * @param filterValue Value to use for matching criteria. For price use format: min,max
     * @return ResponseEntity containing list of matching products and HTTP 200 OK status
     * @throws IllegalArgumentException if filter type is invalid or price format is incorrect
     * @see ProductService#filterByCategory(String)
     * @see ProductService#filterByPriceRange(double, double)
     * @see ProductService#searchByName(String)
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
                throw new IllegalArgumentException("Invalid filter type: " + filterType);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Creates and saves a new product record.
     * Input data is validated before processing.
     * 
     * @param product Validated product data received from request body
     * @return ResponseEntity containing created product data and HTTP 201 Created status
     * @throws IllegalArgumentException if product name is empty or blank
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
     * Updates all details of an existing product.
     * Requires complete product data and validates input before saving.
     * 
     * @param id Unique ID of the product to update
     * @param product Complete updated product information
     * @return ResponseEntity containing updated product data and HTTP 200 OK status
     * @throws ProductNotFoundException if product with given ID does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        Product updated = productService.updateProduct(id, product);
        if (updated == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Updates only specific fields of an existing product.
     * Only provided fields will be changed, remaining data stays the same.
     * 
     * @param id Unique ID of the product to update
     * @param updates Map containing field names and their new values
     * @return ResponseEntity containing updated product data and HTTP 200 OK status
     * @throws ProductNotFoundException if product with given ID does not exist
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Map<String, Object> updates) {

        Product existing = productService.getProductById(id);
        if (existing == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }

        if (updates.containsKey("name"))
            existing.setName((String) updates.get("name"));
        if (updates.containsKey("description"))
            existing.setDescription((String) updates.get("description"));
        if (updates.containsKey("price"))
            existing.setPrice((Double) updates.get("price"));
        if (updates.containsKey("category"))
            existing.setCategory((String) updates.get("category"));
        if (updates.containsKey("stockQuantity"))
            existing.setStockQuantity((Integer) updates.get("stockQuantity"));
        if (updates.containsKey("imageUrl"))
            existing.setImageUrl((String) updates.get("imageUrl"));

        Product saved = productService.updateProduct(id, existing);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    /**
     * Deletes product record from the system.
     * 
     * @param id Unique ID of the product to remove
     * @return ResponseEntity with HTTP 204 No Content status after successful deletion
     * @throws ProductNotFoundException if product with given ID does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}