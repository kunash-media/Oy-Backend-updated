package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    // Create new order
    OrderEntity createOrder(OrderEntity order);

    // Get all orders
    List<OrderEntity> getAllOrders();

    // Get order by ID
    OrderEntity getOrderById(Long orderId);

    // Update order
    OrderEntity updateOrder(Long orderId, OrderEntity order);

    // Delete order
    void deleteOrder(Long orderId);

    // Get orders by customer name
    List<OrderEntity> getOrdersByCustomerName(String customerName);

    // Get orders by status
    List<OrderEntity> getOrdersByStatus(String orderStatus);

    // Get orders by user ID
    List<OrderEntity> getOrdersByUserId(Long userId);
}

