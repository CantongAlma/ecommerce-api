package com.ws101.cantong.EcommerceApi.repository;

import com.ws101.cantong.EcommerceApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Provides CRUD operations and custom query methods for User management.
 * 
 * @author Your Name
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their email address.
     * 
     * @param email the email address to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Checks if a user exists with the given email address.
     * 
     * @param email the email address to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Finds a user by their first name and last name (case-insensitive).
     * 
     * @param firstName the first name to search for
     * @param lastName the last name to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    
    /**
     * Finds users whose first name contains the specified keyword (case-insensitive).
     * 
     * @param keyword the keyword to search for in first names
     * @return list of users with matching first names
     */
    List<User> findByFirstNameContainingIgnoreCase(String keyword);
    
    /**
     * Finds users whose last name contains the specified keyword (case-insensitive).
     * 
     * @param keyword the keyword to search for in last names
     * @return list of users with matching last names
     */
    List<User> findByLastNameContainingIgnoreCase(String keyword);
    
    /**
     * Finds users whose email contains the specified keyword (case-insensitive).
     * 
     * @param keyword the keyword to search for in emails
     * @return list of users with matching emails
     */
    List<User> findByEmailContainingIgnoreCase(String keyword);
    
    /**
     * Finds users who have placed at least one order.
     * Uses JPQL query to find users with orders.
     * 
     * @return list of users who have placed orders
     */
    @Query("SELECT DISTINCT u FROM User u JOIN u.orders o")
    List<User> findUsersWithOrders();
    
    /**
     * Finds users who have NOT placed any orders yet.
     * 
     * @return list of users with no orders
     */
    @Query("SELECT u FROM User u WHERE u.orders IS EMPTY")
    List<User> findUsersWithoutOrders();
    
    /**
     * Finds users by email domain (e.g., @gmail.com, @yahoo.com).
     * 
     * @param domain the email domain to search for (e.g., "@gmail.com")
     * @return list of users with emails matching the domain
     */
    @Query("SELECT u FROM User u WHERE u.email LIKE %:domain")
    List<User> findByEmailDomain(@Param("domain") String domain);
    
    /**
     * Counts the number of orders for a specific user.
     * 
     * @param userId the user ID
     * @return the number of orders placed by the user
     */
    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId")
    long countOrdersByUserId(@Param("userId") Long userId);
    
    /**
     * Finds a user by their email and password (for login authentication).
     * 
     * @param email the user's email
     * @param password the user's password
     * @return Optional containing the user if credentials match
     */
    Optional<User> findByEmailAndPassword(String email, String password);
    
    /**
     * Finds all users ordered by last name alphabetically.
     * 
     * @return list of users sorted by last name
     */
    List<User> findAllByOrderByLastNameAsc();
    
    /**
     * Finds the most recent users (last 5 by ID in descending order).
     * 
     * @return list of the 5 most recently added users
     */
    List<User> findTop5ByOrderByIdDesc();
}