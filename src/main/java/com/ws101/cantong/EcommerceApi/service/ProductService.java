package com.ws101.cantong.EcommerceApi.service;

import com.ws101.cantong.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    
    private final List<Product> productList = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public ProductService() {
        addSampleProducts();
    }
    
    public List<Product> getAllProducts() {
        return new ArrayList<>(productList);
    }
    
    public Product getProductById(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public Product createProduct(Product product) {
        product.setId(idGenerator.getAndIncrement());
        productList.add(product);
        return product;
    }
    
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        if (existing != null) {
            existing.setName(updatedProduct.getName());
            existing.setDescription(updatedProduct.getDescription());
            existing.setPrice(updatedProduct.getPrice());
            existing.setCategory(updatedProduct.getCategory());
            existing.setStockQuantity(updatedProduct.getStockQuantity());
            existing.setImageUrl(updatedProduct.getImageUrl());
            return existing;
        }
        return null;
    }
    
    public boolean deleteProduct(Long id) {
        return productList.removeIf(p -> p.getId().equals(id));
    }
    
    public List<Product> getProductsByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : productList) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return result;
    }
    
    public long getTotalProductCount() {
        return productList.size();
    }
    
    private void addSampleProducts() {
        createProduct(new Product(null, "Laptop", "High-performance laptop", new BigDecimal("999.99"), "Electronics", 50, "https://example.com/laptop.jpg"));
        createProduct(new Product(null, "Smartphone", "Latest smartphone", new BigDecimal("699.99"), "Electronics", 100, "https://example.com/phone.jpg"));
        createProduct(new Product(null, "Headphones", "Wireless headphones", new BigDecimal("199.99"), "Audio", 75, "https://example.com/headphones.jpg"));
    }
}