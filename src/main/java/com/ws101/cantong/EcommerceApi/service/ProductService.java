package com.ws101.cantong.EcommerceApi.service;

import com.ws101.cantong.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for product-related operations.
 * 
 * Provides business logic for creating, retrieving, updating, deleting, 
 * filtering and searching products. This class acts as an intermediary 
 * between the API controller and the data storage.
 * Data is stored temporarily in memory and will be cleared when application restarts.
 * 
 * @author Alma Cantong
 * @see Product
 */
@Service
public class ProductService {
    private List<Product> products;
    private Long nextId; 
    
    /**
     * Constructor: Initializes storage list and loads initial sample data.
     * Starts ID generation from 1.
     */
    public ProductService() {
        products = new ArrayList<>();
        nextId = 1L;
        loadSampleData();
    }
    
    /**
     * Loads 10 predefined sample products into the system.
     * Used for initial testing and demonstration purposes.
     */
    private void loadSampleData() {
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
    
    /**
     * Helper method to add sample products to the storage list.
     * 
     * @param p Product object to be added
     */
    private void addSampleProduct(Product p) {
        products.add(p);
    }
    
    /**
     * Creates and saves a new product.
     * Automatically assigns a unique ID to the product before saving.
     * 
     * @param product The product details to be created
     * @return The saved product including its generated unique ID
     */
    public Product createProduct(Product product) {
        product.setId(nextId);
        products.add(product);
        nextId++;
        return product;
    }
    
    /**
     * Retrieves all existing products from storage.
     * 
     * @return List containing all available products. Returns empty list if no products exist.
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    /**
     * Finds a product using its unique ID.
     * 
     * @param id The unique identifier of the product to find
     * @return Product object if found, returns null if no product matches the given ID
     */
    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Updates information of an existing product.
     * Retains the original product ID while updating all other details.
     * 
     * @param id The ID of the product to update
     * @param updatedProduct Object containing new product data
     * @return Updated product object, returns null if product with given ID does not exist
     */
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
    
    /**
     * Deletes a product from storage using its ID.
     * 
     * @param id The ID of the product to remove
     * @return true if deletion is successful, false if product with given ID is not found
     */
    public boolean deleteProduct(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }
    
    /**
     * Filters products by matching category name.
     * Search is case-insensitive.
     * 
     * @param category The category name to use as filter criteria
     * @return List of products belonging to the specified category.
     * Returns empty list if no products match or category does not exist.
     * @see #filterByPriceRange(double, double)
     * @see #searchByName(String)
     */
    public List<Product> filterByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    /**
     * Filters products within the given price range.
     * Includes products with price equal to minimum or maximum value.
     * 
     * @param minPrice The minimum price limit, must be non-negative
     * @param maxPrice The maximum price limit, must be greater than or equal to minPrice
     * @return List of products whose price falls between the specified range.
     * Returns empty list if no products match the criteria.
     * @throws IllegalArgumentException if minPrice is negative, maxPrice is negative,
     * or minPrice value is higher than maxPrice value
     * @see #filterByCategory(String)
     */
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new IllegalArgumentException("Price values are invalid. Min and Max must be non-negative and min <= max.");
        }
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    /**
     * Searches products by matching keyword in product name.
     * Supports partial text matching and is case-insensitive.
     * 
     * @param keyword The text to search within product names
     * @return List of products whose name contains the given keyword.
     * Returns empty list if no matching product is found.
     */
    public List<Product> searchByName(String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}