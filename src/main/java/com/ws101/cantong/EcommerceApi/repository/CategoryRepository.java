package com.ws101.cantong.EcommerceApi.repository;

import com.ws101.cantong.EcommerceApi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Category entity operations.
 * Provides CRUD operations and custom query methods for Category management.
 * 
 * @author Your Name
 * @version 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Finds a category by its name (case-insensitive).
     * 
     * @param name the category name to search for
     * @return Optional containing the category if found
     */
    Optional<Category> findByNameIgnoreCase(String name);
    
    /**
     * Checks if a category exists with the given name.
     * 
     * @param name the category name to check
     * @return true if category exists, false otherwise
     */
    boolean existsByName(String name);
    
    /**
     * Finds all categories that contain the keyword in their name or description.
     * 
     * @param keyword the search keyword
     * @return list of matching categories
     */
    List<Category> findByNameContainingOrDescriptionContaining(String name, String description);
}