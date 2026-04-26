package com.ws101.cantong.EcommerceApi.controller;
 import com.ws101.cantong.EcommerceApi.model.Product;
 import com.ws101.cantong.EcommerceApi.service.ProductService;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import java.util.List;
 import java.util.Map;
 @RestController
 @RequestMapping("/api/products")
 public class ProductController {
     private final ProductService productService;
     public ProductController(ProductService productService) {
         this.productService = productService;
     }
     @GetMapping
     public ResponseEntity<List<Product>> getAllProducts() {
         List<Product> products = productService.getAllProducts();
         return new ResponseEntity<>(products, HttpStatus.OK);
     }
     @GetMapping("/{id}")
     public ResponseEntity<Product> getProductById(@PathVariable Long id) {
         Product product = productService.getProductById(id);
         if (product != null) {
             return new ResponseEntity<>(product, HttpStatus.OK);
         } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
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
                 double min = Double.parseDouble(range[0]);
                 double max = Double.parseDouble(range[1]);
                 result = productService.filterByPriceRange(min, max);
                 break;
             default:
                 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(result, HttpStatus.OK);
     }
     @PostMapping
     public ResponseEntity<Product> createProduct(@RequestBody Product product) {
         Product created = productService.createProduct(product);
         return new ResponseEntity<>(created, HttpStatus.CREATED); // Status 201
     }
     @PutMapping("/{id}")
     public ResponseEntity<Product> updateProduct(
             @PathVariable Long id,
             @RequestBody Product product) {
         
         Product updated = productService.updateProduct(id, product);
         if (updated != null) {
             return new ResponseEntity<>(updated, HttpStatus.OK);
         } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
     }
     @PatchMapping("/{id}")
     public ResponseEntity<Product> partialUpdateProduct(
             @PathVariable Long id,
             @RequestBody Map<String, Object> updates) {
         
         Product existing = productService.getProductById(id);
         if (existing == null) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         if (updates.containsKey("name"))
             existing.setName((String) updates.get("name"));
         if (updates.containsKey("description"))
             existing.setDescription((String) updates.get("description"));
         if (updates.containsKey("price"))
             existing.setPrice((Double) updates.get("price"));
         if (updates.containsKey("category"))
             existing.setCategory((String) updates.get("category"));
         if (updates.containsKey("stockQuantity"))
             existing.setStockQuantity((Integer) updates.get("stockQuantity"));
         if (updates.containsKey("imageUrl"))
             existing.setImageUrl((String) updates.get("imageUrl"));
         Product saved = productService.updateProduct(id, existing);
         return new ResponseEntity<>(saved, HttpStatus.OK);
     }
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
         boolean deleted = productService.deleteProduct(id);
         if (deleted) {
             return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Status 204
         } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
     }
 }