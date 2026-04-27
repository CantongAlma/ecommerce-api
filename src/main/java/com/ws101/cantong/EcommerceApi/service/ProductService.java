package com.ws101.cantong.EcommerceApi.service;

import com.ws101.cantong.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Product data and business logic.
 * 
 * STORAGE STRATEGY:
 * - Data is stored temporarily in memory using an ArrayList<Product>.
 * - All data will be lost when the application stops or restarts.
 * - Unique IDs are generated automatically using a counter variable 'nextId'.
 * - IDs are sequential and guaranteed to be unique even if items are deleted.
 * - Initialized with 10 sample products when application starts.
 */
@Service
public class ProductService {
    private List<Product> products;
    private Long nextId; 
    
    /**
     * Constructor: Initializes storage list and loads initial sample data.
     * ID counter starts from 1.
     */
    public ProductService() {
        products = new ArrayList<>();
        nextId = 1L; // Start counting from 1
        loadSampleData();
    }
    
    /**
     * Loads 10 predefined sample products into the system.
     * Used for testing and demonstration purposes.
     */
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
    
    /**
     * Helper method to add sample products to the list.
     * 
     * @param p Product object to be added
     */
    private void addSampleProduct(Product p) {
        products.add(p);
    }
    
    /**
     * Creates and saves a new product to storage.
     * Automatically assigns a unique ID to the product.
     * 
     * @param product The product data to be created
     * @return The created product with generated ID
     */
    public Product createProduct(Product product) {
        product.setId(nextId);
        products.add(product);
        nextId++; // FIXED: Added this line to increment the counter
        return product;
    }
    
    /**
     * Retrieves all existing products from storage.
     * 
     * @return List containing all product objects
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    /**
     * Finds a specific product using its ID.
     * 
     * @param id The unique identifier of the required product
     * @return Product object if found, null if ID does not exist
     */
    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Updates information of an existing product.
     * Keeps the original ID while updating all other details.
     * 
     * @param id ID of the product to update
     * @param updatedProduct Object containing new product data
     * @return Updated product object, null if product not found
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
     * Deletes a product from the storage using its ID.
     * 
     * @param id ID of the product to be removed
     * @return true if deleted successfully, false if product not found
     */
    public boolean deleteProduct(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }
    
    /**
     * Filters products by matching exact category name.
     * Search is case-insensitive.
     * 
     * @param category Category name to filter
     * @return List of products belonging to specified category
     */
    public List<Product> filterByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    /**
     * Filters products within the given price range.
     * 
     * @param minPrice Minimum price limit
     * @param maxPrice Maximum price limit
     * @return List of products whose price is between min and max value
     */
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    /**
     * Searches products by matching keyword in product name.
     * Search is case-insensitive and works with partial text.
     * 
     * @param keyword Text to search in product name
     * @return List of products with matching name
     */
    public List<Product> searchByName(String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}