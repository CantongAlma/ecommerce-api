package com.ws101.cantong.EcommerceApi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing an Order in the e-commerce system.
 * An order is created when a customer purchases products and contains one or more order items.
 * 
 * <p>Relationships:</p>
 * <ul>
 *   <li><b>Many-to-One</b> with {@link User} - Each order belongs to exactly one user/customer</li>
 *   <li><b>One-to-Many</b> with {@link OrderItem} - Each order can contain multiple order items</li>
 * </ul>
 * 
 * <p>This entity manages the total amount calculation automatically when order items
 * are added or removed using the provided helper methods.</p>
 * 
 * @author Your Name
 * @version 1.0
 * @see User
 * @see OrderItem
 * @see OrderStatus
 */
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Many-to-One relationship with User.
     * Multiple orders can belong to one user/customer.
     * 
     * <p>The foreign key column 'user_id' cannot be null, meaning every order
     * must be associated with a registered user.</p>
     * 
     * <p>Fetch type defaults to EAGER for @ManyToOne unless specified otherwise.</p>
     * 
     * @see User
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * One-to-Many relationship with OrderItem.
     * One order can contain multiple order items (each representing a product with quantity).
     * 
     * <p>The 'mappedBy' attribute indicates this is the inverse side of the relationship,
     * with the owning side being the 'order' field in the OrderItem entity.</p>
     * 
     * <p>CascadeType.ALL means all JPA operations performed on an Order will cascade
     * to all its associated OrderItems (e.g., deleting an order deletes its order items).</p>
     * 
     * <p>Fetch type LAZY means order items are loaded only when accessed, improving performance.</p>
     * 
     * <p>orphanRemoval = true means any OrderItem removed from this collection will
     * be automatically deleted from the database.</p>
     * 
     * @see OrderItem
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    /**
     * Order status using an enumeration to ensure only valid status values.
     * Stored as String in the database for readability.
     * 
     * @see OrderStatus
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @Column(name = "total_amount", precision = 10, scale = 2)
    private Double totalAmount;
    
    /**
     * Default constructor required by JPA.
     */
    public Order() {
    }
    
    /**
     * Parameterized constructor for creating an order with essential information.
     * 
     * @param user the user/customer placing the order
     * @param orderDate the date and time when the order was placed
     * @param status the initial status of the order
     * @param totalAmount the total amount of the order
     */
    public Order(User user, LocalDateTime orderDate, OrderStatus status, Double totalAmount) {
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }
    
    /**
     * Helper method to maintain bidirectional relationship consistency.
     * Adds an order item to this order and sets the order item's order reference to this order.
     * Automatically recalculates the total amount after adding the item.
     * 
     * <p>Always use this method instead of directly manipulating the orderItems list
     * to ensure both sides of the relationship are synchronized and the total is accurate.</p>
     * 
     * @param orderItem the order item to add to this order
     * @see #removeOrderItem(OrderItem)
     * @see #calculateTotalAmount()
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        calculateTotalAmount();
    }
    
    /**
     * Helper method to maintain bidirectional relationship consistency.
     * Removes an order item from this order and clears the order item's order reference.
     * Automatically recalculates the total amount after removing the item.
     * 
     * <p>Always use this method instead of directly manipulating the orderItems list
     * to ensure both sides of the relationship are properly synchronized.</p>
     * 
     * @param orderItem the order item to remove from this order
     * @see #addOrderItem(OrderItem)
     * @see #calculateTotalAmount()
     */
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
        calculateTotalAmount();
    }
    
    /**
     * Calculates the total amount of the order by summing the total price of all order items.
     * This method is automatically called whenever order items are added or removed.
     * 
     * <p>The total amount is stored as a Double with precision 10 and scale 2 in the database.</p>
     * 
     * @see #addOrderItem(OrderItem)
     * @see #removeOrderItem(OrderItem)
     * @see OrderItem#getTotalPrice()
     */
    private void calculateTotalAmount() {
        this.totalAmount = orderItems.stream()
            .mapToDouble(item -> item.getTotalPrice().doubleValue())
            .sum();
    }
    
    /**
     * Gets the order ID.
     * 
     * @return the order ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the order ID.
     * 
     * @param id the order ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the user who placed this order.
     * 
     * @return the user associated with this order
     * @see User
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Sets the user who placed this order.
     * 
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Gets the list of order items in this order.
     * 
     * @return list of order items
     * @see OrderItem
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    /**
     * Sets the list of order items for this order.
     * Automatically recalculates the total amount after setting the list.
     * 
     * <p>Note: It's recommended to use the {@link #addOrderItem(OrderItem)} and
     * {@link #removeOrderItem(OrderItem)} helper methods to maintain
     * bidirectional relationship consistency.</p>
     * 
     * @param orderItems the list of order items to set
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        calculateTotalAmount();
    }
    
    /**
     * Gets the date and time when the order was placed.
     * 
     * @return the order date and time
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    /**
     * Sets the date and time when the order was placed.
     * 
     * @param orderDate the order date and time to set
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    
    /**
     * Gets the current status of the order.
     * 
     * @return the order status
     * @see OrderStatus
     */
    public OrderStatus getStatus() {
        return status;
    }
    
    /**
     * Sets the status of the order.
     * 
     * @param status the order status to set
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    /**
     * Gets the total amount of the order.
     * This value is automatically calculated based on the order items.
     * 
     * @return the total amount of the order
     */
    public Double getTotalAmount() {
        return totalAmount;
    }
    
    /**
     * Sets the total amount of the order.
     * 
     * <p>Note: It's recommended to let the order calculate its own total amount
     * via the {@link #calculateTotalAmount()} method instead of setting it manually.</p>
     * 
     * @param totalAmount the total amount to set
     */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}