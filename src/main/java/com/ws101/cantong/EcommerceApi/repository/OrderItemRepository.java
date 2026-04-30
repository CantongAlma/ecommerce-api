package com.ws101.cantong.EcommerceApi.repository;

import com.ws101.cantong.EcommerceApi.model.OrderItem;
import com.ws101.cantong.EcommerceApi.model.Order;
import com.ws101.cantong.EcommerceApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for OrderItem entity operations.
 * Provides CRUD operations and custom query methods for OrderItem management.
 * 
 * @author Your Name
 * @version 1.0
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Finds all order items for a specific order.
     * 
     * @param order the order
     * @return list of order items in the specified order
     */
    List<OrderItem> findByOrder(Order order);
    
    /**
     * Finds all order items for a specific product.
     * 
     * @param product the product
     * @return list of order items containing the specified product
     */
    List<OrderItem> findByProduct(Product product);
    
    /**
     * Finds all order items for a specific order ID.
     * 
     * @param orderId the order ID
     * @return list of order items in the specified order
     */
    List<OrderItem> findByOrderId(Long orderId);
    
    /**
     * Finds all order items for a specific product ID.
     * 
     * @param productId the product ID
     * @return list of order items containing the specified product
     */
    List<OrderItem> findByProductId(Long productId);
}