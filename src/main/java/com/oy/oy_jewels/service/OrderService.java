package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.CreateOrderRequest;
import com.oy.oy_jewels.dto.request.ExchangeRequestDTO;
import com.oy.oy_jewels.dto.request.ReturnRequestDTO;
import com.oy.oy_jewels.dto.request.UpdateOrderRequest;
import com.oy.oy_jewels.dto.response.AllOrderResponseDTO;
import com.oy.oy_jewels.dto.response.OrderResponse;
import com.oy.oy_jewels.entity.OrderEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {

    // Core CRUD operations
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long orderId);

    List<AllOrderResponseDTO> getAllOrders();

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
    String createReturnOrder(Long orderId, ReturnRequestDTO returnRequest);
    String createExchangeOrder(Long orderId, ExchangeRequestDTO exchangeRequest);
}