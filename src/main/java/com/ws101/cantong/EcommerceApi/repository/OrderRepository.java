package com.ws101.cantong.EcommerceApi.repository;

import com.ws101.cantong.EcommerceApi.entity.User;  // CHANGED: from model.User to entity.User
import com.ws101.cantong.EcommerceApi.model.Order;
import com.ws101.cantong.EcommerceApi.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Order entity operations.
 * Provides CRUD operations and custom query methods for Order management.
 * 
 * @author Your Name
 * @version 1.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Finds all orders placed by a specific user.
     * 
     * @param user the user whose orders to retrieve
     * @return list of orders for the specified user
     */
    List<Order> findByUser(User user);
    
    /**
     * Finds all orders placed by a specific user ID.
     * 
     * @param userId the ID of the user
     * @return list of orders for the specified user
     */
    List<Order> findByUserId(Long userId);
    
    /**
     * Finds all orders with a specific status.
     * 
     * @param status the order status to filter by
     * @return list of orders with the specified status
     */
    List<Order> findByStatus(OrderStatus status);
    
    /**
     * Finds all orders placed after a specific date.
     * 
     * @param date the date to compare against
     * @return list of orders placed after the specified date
     */
    List<Order> findByOrderDateAfter(LocalDateTime date);
    
    /**
     * Finds all orders placed before a specific date.
     * 
     * @param date the date to compare against
     * @return list of orders placed before the specified date
     */
    List<Order> findByOrderDateBefore(LocalDateTime date);
    
    /**
     * Finds all orders placed between two dates.
     * 
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of orders placed within the date range
     */
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Finds all orders for a specific user with a specific status.
     * 
     * @param user the user
     * @param status the order status
     * @return list of orders matching both criteria
     */
    List<Order> findByUserAndStatus(User user, OrderStatus status);
    
    /**
     * Finds all orders with total amount greater than or equal to the specified amount.
     * 
     * @param amount the minimum total amount
     * @return list of orders with total amount >= specified value
     */
    List<Order> findByTotalAmountGreaterThanEqual(Double amount);
    
    /**
     * Counts how many orders a user has placed.
     * 
     * @param user the user
     * @return the number of orders for the user
     */
    long countByUser(User user);
    
    /**
     * Counts how many orders have a specific status.
     * 
     * @param status the order status
     * @return the number of orders with the specified status
     */
    long countByStatus(OrderStatus status);
    
    /**
     * Checks if a user has any orders with a specific status.
     * 
     * @param user the user
     * @param status the order status to check
     * @return true if the user has at least one order with the status
     */
    boolean existsByUserAndStatus(User user, OrderStatus status);
}