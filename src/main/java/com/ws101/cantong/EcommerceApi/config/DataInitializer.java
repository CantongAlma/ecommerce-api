package com.ws101.cantong.EcommerceApi.config;

import com.ws101.cantong.EcommerceApi.entity.Role;
import com.ws101.cantong.EcommerceApi.entity.User;
import com.ws101.cantong.EcommerceApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
       
        if (!userRepository.existsByEmail("admin@ecommerce.com")) {
            User admin = new User();
            admin.setEmail("admin@ecommerce.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setRoles(Set.of(Role.ADMIN, Role.USER, Role.SELLER));
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("✅ Default ADMIN created: admin@ecommerce.com / admin123");
        }
        
      
        if (!userRepository.existsByEmail("user@ecommerce.com")) {
            User user = new User();
            user.setEmail("user@ecommerce.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setFirstName("Regular");
            user.setLastName("User");
            user.setRoles(Set.of(Role.USER));
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("✅ Default USER created: user@ecommerce.com / user123");
        }
        
        
        if (!userRepository.existsByEmail("seller@ecommerce.com")) {
            User seller = new User();
            seller.setEmail("seller@ecommerce.com");
            seller.setPassword(passwordEncoder.encode("seller123"));
            seller.setFirstName("Product");
            seller.setLastName("Seller");
            seller.setRoles(Set.of(Role.SELLER, Role.USER));
            seller.setEnabled(true);
            userRepository.save(seller);
            System.out.println("✅ Default SELLER created: seller@ecommerce.com / seller123");
        }
    }
    
}