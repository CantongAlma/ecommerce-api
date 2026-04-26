package com.ws101.cantong.EcommerceApi.services;

import com.ws101.cantong.EcommerceApi.models.Product;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    
    // In-memory storage using ArrayList
    private final List<Product> productList = new ArrayList<>();
    
    // ID generator (since no database auto-increment)
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    // Constructor - adds some sample products
    public ProductService() {
        // Add sample products when service starts
        addSampleProducts();
    }
    
    // Get all products
    public List<Product> getAllProducts() {
        return new ArrayList<>(productList);
    }
    
    // Get product by ID
    public Product getProductById(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    // Create new product
    public Product createProduct(Product product) {
        Long newId = idGenerator.getAndIncrement();
        product.setId(newId);
        productList.add(product);
        return product;
    }
    
    // Update existing product
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
            return existingProduct;
        }
        return null;
    }
    
    // Delete product
    public boolean deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            return productList.remove(product);
        }
        return false;
    }
    
    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        List<Product> productsByCategory = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory;
    }
    
    // Get products with price less than given amount
    public List<Product> getProductsByMaxPrice(BigDecimal maxPrice) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : productList) {
            if (product.getPrice().compareTo(maxPrice) <= 0) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
    
    // Get products in stock (stockQuantity > 0)
    public List<Product> getProductsInStock() {
        List<Product> inStockProducts = new ArrayList<>();
        for (Product product : productList) {
            if (product.getStockQuantity() > 0) {
                inStockProducts.add(product);
            }
        }
        return inStockProducts;
    }
    
    // Update stock quantity
    public Product updateStock(Long id, int newStockQuantity) {
        Product product = getProductById(id);
        if (product != null) {
            product.setStockQuantity(newStockQuantity);
            return product;
        }
        return null;
    }
    
    // Search products by name (contains)
    public List<Product> searchProductsByName(String nameKeyword) {
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(nameKeyword.toLowerCase())) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }
    
    // Add sample products for testing
    private void addSampleProducts() {
        createProduct(new Product(null, "Laptop", "High-performance laptop with 16GB RAM", 
                new BigDecimal("999.99"), "Electronics", 50, "https://example.com/laptop.jpg"));
        
        createProduct(new Product(null, "Smartphone", "Latest model with 128GB storage", 
                new BigDecimal("699.99"), "Electronics", 100, "https://example.com/phone.jpg"));
        
        createProduct(new Product(null, "Headphones", "Noise-cancelling wireless headphones", 
                new BigDecimal("199.99"), "Audio", 75, "https://example.com/headphones.jpg"));
        
        createProduct(new Product(null, "Coffee Mug", "Ceramic coffee mug with lid", 
                new BigDecimal("14.99"), "Kitchen", 200, "https://example.com/mug.jpg"));
        
        createProduct(new Product(null, "Backpack", "Waterproof laptop backpack", 
                new BigDecimal("49.99"), "Accessories", 30, "https://example.com/backpack.jpg"));
    }
    
    // Get total number of products
    public long getTotalProductCount() {
        return productList.size();
    }
    
    // Clear all products (for testing)
    public void clearAllProducts() {
        productList.clear();
        idGenerator.set(1);
    }
}
