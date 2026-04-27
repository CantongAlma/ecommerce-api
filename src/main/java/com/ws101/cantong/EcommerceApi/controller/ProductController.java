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
 * REST Controller for Product API.
 * 
 * STATUS CODE USAGE:
 * 200 OK      - Successful GET requests or updates.
 * 201 Created - Successfully created a new resource (POST).
 * 204 No Content - Successfully deleted, nothing to return.
 * 400 Bad Request - Invalid input data or parameters.
 * 404 Not Found - Resource ID does not exist.
 * 500 Internal Server Error - Server-side failure.
 */

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

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

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid@RequestBody Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid@RequestBody Product product) {

        Product updated = productService.updateProduct(id, product);
        if (updated == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(
            @PathVariable Long id,
            @Valid@RequestBody Map<String, Object> updates) {

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}