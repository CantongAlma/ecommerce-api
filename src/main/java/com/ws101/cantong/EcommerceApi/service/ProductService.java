package com.ws101.cantong.EcommerceApi.service;

import com.ws101.cantong.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Product data.
 * 
 * STORAGE STRATEGY:
 * - Data is stored temporarily in memory using an ArrayList<Product>.
 * - Data will be lost when the application stops or restarts.
 * - Unique IDs are generated using a counter variable 'nextId'.
 * - IDs are sequential and guaranteed to be unique even if items are deleted.
 * - Initialized with 10 sample products upon creation.
 */
@Service
public class ProductService {
    private List<Product> products;
    private Long nextId; 
    
    public ProductService() {
        products = new ArrayList<>();
        nextId = 1L; // Start counting from 1
        loadSampleData();
    }
    
    private void loadSampleData() {
        // When adding sample data, we use the counter and increment it
        addSampleProduct(new Product(nextId++, "Wireless Headphones", "Noise cancelling over-ear headphones", 59.99, "Electronics", 15, "url1.jpg"));
        addSampleProduct(new Product(nextId++, "Mechanical Keyboard", "RGB backlit gaming keyboard", 24.99, "Electronics", 20, "url2.jpg"));
        addSampleProduct(new Product(nextId++, "Cotton T-Shirt", "Comfortable plain cotton t-shirt", 3.99, "Clothing", 50, "url3.jpg"));
        addSampleProduct(new Product(nextId++, "Running Shoes", "Lightweight breathable running shoes", 32.00, "Footwear", 12, "url4.jpg"));
        addSampleProduct(new Product(nextId++, "Coffee Mug", "Ceramic mug 350ml", 1.99, "Home & Kitchen", 100, "url5.jpg"));
        addSampleProduct(new Product(nextId++, "Smart Watch", "Fitness tracker with heart rate monitor", 75.00, "Electronics", 8, "url6.jpg"));
        addSampleProduct(new Product(nextId++, "Denim Jeans", "Slim fit blue jeans", 8.99, "Clothing", 25, "url7.jpg"));
        addSampleProduct(new Product(nextId++, "Desk Lamp", "LED adjustable desk lamp", 4.50, "Home & Kitchen", 30, "url8.jpg"));
        addSampleProduct(new Product(nextId++, "Backpack", "Waterproof travel backpack", 12.00, "Accessories", 18, "url9.jpg"));
        addSampleProduct(new Product(nextId++, "Bluetooth Speaker", "Portable waterproof speaker", 18.00, "Electronics", 22, "url10.jpg"));
    }
    
    private void addSampleProduct(Product p) {
        products.add(p);
    }
    
    public Product createProduct(Product product) {
        product.setId(nextId);
        products.add(product);
        nextId++; // FIXED: Added this line to increment the counter
        return product;
    }
    
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public Product updateProduct(Long id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product existing = products.get(i);
            if (existing.getId().equals(id)) {
                updatedProduct.setId(id);
                products.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }
    
    public boolean deleteProduct(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }
    
    public List<Product> filterByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    public List<Product> searchByName(String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}