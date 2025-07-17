package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.CreateOrderRequest;
import com.oy.oy_jewels.dto.request.UpdateOrderRequest;
import com.oy.oy_jewels.dto.response.OrderResponse;
import com.oy.oy_jewels.entity.OrderEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {

    // Core CRUD operations
    OrderResponse createOrder(CreateOrderRequest request);
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderById(Long orderId);
    OrderResponse updateOrder(Long orderId, UpdateOrderRequest request);
    OrderResponse patchOrder(Long orderId, Map<String, Object> updates);
    void deleteOrder(Long orderId);

    // Query operations
    List<OrderResponse> getOrdersByUserId(Long userId);
    List<OrderResponse> getOrdersByStatus(String orderStatus);
    List<OrderResponse> getOrdersByProductId(Long productId);
    List<OrderResponse> getOrdersByDateRange(LocalDate startDate, LocalDate endDate);
    List<OrderEntity> findOrdersByProductId(Long productId);

    void cancelOrder(Long orderId);

    public void cancelOrderByShiprocketId(String shiprocketOrderId);
    String createReturnOrder(Long orderId, Map<String, Object> returnRequest);
    String createExchangeOrder(Long orderId, Map<String, Object> exchangeRequest);
}