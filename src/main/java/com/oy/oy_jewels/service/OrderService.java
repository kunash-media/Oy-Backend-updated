package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.CreateOrderRequest;
import com.oy.oy_jewels.dto.request.UpdateOrderRequest;
import com.oy.oy_jewels.dto.response.OrderResponse;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderById(Long orderId);
    OrderResponse updateOrder(Long orderId, UpdateOrderRequest request);
    void deleteOrder(Long orderId);
    List<OrderResponse> getOrdersByUserId(Long userId);
    List<OrderResponse> getOrdersByStatus(String orderStatus);
    List<OrderResponse> getOrdersByProductId(Long productId);
    List<OrderResponse> getOrdersByDateRange(LocalDate startDate, LocalDate endDate);
}