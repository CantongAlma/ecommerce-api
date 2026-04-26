package com.ws101.cantong.EcommerceApi.controller;

import com.ws101.cantong.EcommerceApi.model.Product;
import com.ws101.cantong.EcommerceApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
    
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? "Product deleted" : "Product not found";
    }
    
    @GetMapping("/category/{category}")
    public List<Product> filterByCategory(@PathVariable String category) {
        return productService.filterByCategory(category);
    }
    
    @GetMapping("/price-range")
    public List<Product> filterByPriceRange(@RequestParam double min, @RequestParam double max) {
        return productService.filterByPriceRange(min, max);
    }
    
    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String keyword) {
        return productService.searchByName(keyword);
    }
}