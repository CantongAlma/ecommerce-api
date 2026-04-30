package com.ws101.cantong.EcommerceApi.config;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.servlet.config.annotation.CorsRegistry;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 @Configuration // Marks this class as a Spring configuration class
 public class webconfig implements WebMvcConfigurer {
     @Override
     public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/api/**") // Apply CORS to all /api endpoints
                 .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                 .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                 .allowedHeaders("*") 
                 .allowCredentials(true) 
                 .maxAge(3600); 
     }
 }