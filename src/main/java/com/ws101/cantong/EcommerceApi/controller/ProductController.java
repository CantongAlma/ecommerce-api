package com.ws101.cantong.EcommerceApi.controller;

import com.ws101.cantong.EcommerceApi.dto.CreateProductDto;
import com.ws101.cantong.EcommerceApi.dto.ProductListingDto;
import com.ws101.cantong.EcommerceApi.dto.UpdateProductDto;
import com.ws101.cantong.EcommerceApi.model.Category;
import com.ws101.cantong.EcommerceApi.model.Product;
import com.ws101.cantong.EcommerceApi.service.ProductService;
import com.ws101.cantong.EcommerceApi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductListingDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductListingDto> productDTOs = products.stream()
            .map(p -> new ProductListingDto(p.getId(), p.getName(), p.getPrice(), p.getImageUrl()))
            .collect(Collectors.toList());
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue) {

        List<Product> result;

        switch (filterType.toLowerCase()) {
            case "category":
                result = productService.filterByCategory(filterValue);
                break;
            case "name":
                result = productService.searchByName(filterValue);
                break;
            case "price":
                String[] range = filterValue.split(",");
                if (range.length != 2) {
                    throw new IllegalArgumentException("Invalid price format. Use: min,max (e.g. 100,500)");
                }
                double min = Double.parseDouble(range[0]);
                double max = Double.parseDouble(range[1]);
                result = productService.filterByPriceRange(min, max);
                break;
            default:
                throw new IllegalArgumentException("Invalid filter type: " + filterType);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductDto createDto) {
        Product product = new Product();
        product.setName(createDto.getName());
        product.setDescription(createDto.getDescription());
        product.setPrice(createDto.getPrice());
        product.setStockQuantity(createDto.getStockQuantity());
        product.setImageUrl(createDto.getImageUrl());
        
        // Set category if provided - using findByNameIgnoreCase
        if (createDto.getCategory() != null && !createDto.getCategory().isEmpty()) {
            Category category = categoryRepository.findByNameIgnoreCase(createDto.getCategory())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(createDto.getCategory());
                    return categoryRepository.save(newCategory);
                });
            product.setCategory(category);
        }
        
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductDto updateDto) {
        
        Product existing = productService.getProductById(id);
        
        if (updateDto.getName() != null) {
            existing.setName(updateDto.getName());
        }
        if (updateDto.getDescription() != null) {
            existing.setDescription(updateDto.getDescription());
        }
        if (updateDto.getPrice() != null) {
            existing.setPrice(updateDto.getPrice());
        }
        if (updateDto.getStockQuantity() != null) {
            existing.setStockQuantity(updateDto.getStockQuantity());
        }
        if (updateDto.getImageUrl() != null) {
            existing.setImageUrl(updateDto.getImageUrl());
        }
        if (updateDto.getCategory() != null && !updateDto.getCategory().isEmpty()) {
            // Using findByNameIgnoreCase
            Category category = categoryRepository.findByNameIgnoreCase(updateDto.getCategory())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(updateDto.getCategory());
                    return categoryRepository.save(newCategory);
                });
            existing.setCategory(category);
        }
        
        Product updated = productService.updateProduct(id, existing);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
            
        Product existing = productService.getProductById(id);

        if (updates.containsKey("name")) {
            existing.setName((String) updates.get("name"));
        }
        if (updates.containsKey("description")) {
            existing.setDescription((String) updates.get("description"));
        }
        if (updates.containsKey("price")) {
            Object priceValue = updates.get("price");
            if (priceValue instanceof Double) {
                existing.setPrice(BigDecimal.valueOf((Double) priceValue));
            } else if (priceValue instanceof Integer) {
                existing.setPrice(BigDecimal.valueOf((Integer) priceValue));
            } else if (priceValue instanceof String) {
                existing.setPrice(new BigDecimal((String) priceValue));
            }
        }
        if (updates.containsKey("stockQuantity")) {
            existing.setStockQuantity((Integer) updates.get("stockQuantity"));
        }
        if (updates.containsKey("imageUrl")) {
            existing.setImageUrl((String) updates.get("imageUrl"));
        }
        if (updates.containsKey("category")) {
            String categoryName = (String) updates.get("category");
            // Using findByNameIgnoreCase
            Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    return categoryRepository.save(newCategory);
                });
            existing.setCategory(category);
        }

        Product saved = productService.updateProduct(id, existing);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts(
            @RequestParam(defaultValue = "10") int threshold) {
        List<Product> products = productService.getLowStockProducts(threshold);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}