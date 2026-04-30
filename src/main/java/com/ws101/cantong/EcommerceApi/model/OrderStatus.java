package com.ws101.cantong.EcommerceApi.model;
/**
 * Enumeration representing the possible statuses of an order.
 * 
 * @author Your Name
 * @version 1.0
 */

public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    REFUNDED
}