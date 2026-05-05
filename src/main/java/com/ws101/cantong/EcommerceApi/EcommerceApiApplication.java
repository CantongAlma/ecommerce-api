package com.ws101.cantong.EcommerceApi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ws101.cantong.EcommerceApi.model.Product;
import com.ws101.cantong.EcommerceApi.model.Category;
import com.ws101.cantong.EcommerceApi.repository.ProductRepository;
import com.ws101.cantong.EcommerceApi.repository.CategoryRepository;

@SpringBootApplication
public class EcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(ProductRepository productRepo, CategoryRepository categoryRepo) {
        return args -> {
            System.out.println("\n========================================");
            System.out.println("🚀 STARTING APPLICATION");
            System.out.println("========================================\n");
            
            // Check if products exist
            if (productRepo.count() == 0) {
                System.out.println("📦 Loading sample data...\n");
                
                // Create categories
                Category electronics = new Category("Electronics");
                Category clothing = new Category("Clothing");
                Category accessories = new Category("Accessories");
                Category beauty = new Category("Beauty");
                
                categoryRepo.save(electronics);
                categoryRepo.save(clothing);
                categoryRepo.save(accessories);
                categoryRepo.save(beauty);
                
                // Create products
                Product p1 = new Product("Wireless Headphones", "High quality sound", 19.99, 100);
                p1.setCategory(electronics);
                
                Product p2 = new Product("Smart Watch", "Fitness tracker", 29.99, 50);
                p2.setCategory(electronics);
                
                Product p3 = new Product("Shoes", "Comfortable sport shoes", 39.99, 30);
                p3.setCategory(clothing);
                
                Product p4 = new Product("Bluetooth Speaker", "Clear sound", 49.99, 75);
                p4.setCategory(electronics);
                
                Product p5 = new Product("Backpack", "Durable bag", 59.99, 25);
                p5.setCategory(accessories);
                
                Product p6 = new Product("Umbrella", "Waterproof", 69.99, 40);
                p6.setCategory(accessories);
                
                Product p7 = new Product("Handbag", "Elegant bag", 69.99, 15);
                p7.setCategory(accessories);
                
                Product p8 = new Product("Thumbler", "Insulated bottle", 49.99, 60);
                p8.setCategory(beauty);
                
                Product p9 = new Product("Perfume", "Long lasting scent", 19.99, 80);
                p9.setCategory(beauty);
                
                productRepo.save(p1);
                productRepo.save(p2);
                productRepo.save(p3);
                productRepo.save(p4);
                productRepo.save(p5);
                productRepo.save(p6);
                productRepo.save(p7);
                productRepo.save(p8);
                productRepo.save(p9);
                
                System.out.println("✅ Loaded " + productRepo.count() + " products!");
            } else {
                System.out.println("✅ Database already has " + productRepo.count() + " products");
            }
            
            System.out.println("\n========================================");
            System.out.println("🌐 API is ready!");
            System.out.println("📡 Go to: http://localhost:8080/api/products");
            System.out.println("========================================\n");
        };
    }
}