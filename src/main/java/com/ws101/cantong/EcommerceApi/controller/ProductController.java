package com.ws101.cantong.EcommerceApi.controllers;

import com.ws101.cantong.EcommerceApi.models.Product;
import com.ws101.cantong.EcommerceApi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    // Get product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        return product;
    }
    
    // Create new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    
    // Update product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        return updatedProduct;
    }
    
    // Delete product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        return "Product deleted with id: " + id;
    }
    
    // Get products by category
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }
    
    // Get products by max price
    @GetMapping("/price/max/{maxPrice}")
    public List<Product> getProductsByMaxPrice(@PathVariable BigDecimal maxPrice) {
        return productService.getProductsByMaxPrice(maxPrice);
    }
    
    // Get products in stock
    @GetMapping("/in-stock")
    public List<Product> getProductsInStock() {
        return productService.getProductsInStock();
    }
    
    // Search products by name
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProductsByName(keyword);
    }
    
    // Update stock quantity
    @PatchMapping("/{id}/stock")
    public Product updateStock(@PathVariable Long id, @RequestParam int quantity) {
        Product product = productService.updateStock(id, quantity);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        return product;
    }
    
    // Get total product count
    @GetMapping("/count")
    public String getTotalCount() {
        return "Total products: " + productService.getTotalProductCount();
    }
}