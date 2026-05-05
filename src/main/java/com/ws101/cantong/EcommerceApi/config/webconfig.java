package com.ws101.cantong.EcommerceApi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all paths starting with /api/
        registry.addMapping("/api/**")
                // Allow your frontend address
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")
                // Allow these request methods
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Allow all headers
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}