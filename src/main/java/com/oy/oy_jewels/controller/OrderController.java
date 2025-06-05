package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.CreateOrderRequest;
import com.oy.oy_jewels.dto.request.UpdateOrderRequest;
import com.oy.oy_jewels.dto.response.OrderResponse;
import com.oy.oy_jewels.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Create a new order
     * POST /api/orders/create
     */
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            OrderResponse createdOrder = orderService.createOrder(request);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all orders
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Get order by ID
     * GET /api/orders/{orderId}
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderResponse order = orderService.getOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update order
     * PUT /api/orders/{orderId}
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long orderId,
                                                     @RequestBody UpdateOrderRequest request) {
        try {
            OrderResponse updatedOrder = orderService.updateOrder(orderId, request);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete order
     * DELETE /api/orders/{orderId}
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Order deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Order not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get orders by user ID
     * GET /api/orders/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Get orders by status
     * GET /api/orders/status/{orderStatus}
     */
    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable String orderStatus) {
        List<OrderResponse> orders = orderService.getOrdersByStatus(orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Get orders by product ID
     * GET /api/orders/product/{productId}
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByProductId(@PathVariable Long productId) {
        List<OrderResponse> orders = orderService.getOrdersByProductId(productId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Get orders by date range
     * GET /api/orders/date-range?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<OrderResponse>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OrderResponse> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}