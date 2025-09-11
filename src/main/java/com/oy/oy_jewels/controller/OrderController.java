package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.CreateOrderRequest;
import com.oy.oy_jewels.dto.request.ExchangeRequestDTO;
import com.oy.oy_jewels.dto.request.ReturnRequestDTO;
import com.oy.oy_jewels.dto.request.UpdateOrderRequest;
import com.oy.oy_jewels.dto.response.AllOrderResponseDTO;
import com.oy.oy_jewels.dto.response.OrderResponse;
import com.oy.oy_jewels.service.OrderService;
import com.oy.oy_jewels.service.serviceImpl.ShiprocketService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    private ShiprocketService shiprocketService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            logger.info("Creating order for user: {}", request.getUserId());
            OrderResponse response = orderService.createOrder(request);

            // Handle insufficient stock response
            if ("insufficient_stock".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error creating order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OrderResponse("error", "Failed to create order: " + e.getMessage(), null));
        }
    }

    //------- get dashboard stats data -----------//
    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> getOrderStatistics() {
        Map<String, Object> stats = orderService.getOrderStatistics();
        return ResponseEntity.ok(stats);
    }

    // Get order by ID
    @GetMapping("/get-order/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        try {
            logger.info("Fetching order with id: {}", orderId);
            OrderResponse order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            logger.error("Order not found with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OrderResponse("error", e.getMessage(), null));
        } catch (Exception e) {
            logger.error("Error fetching order with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OrderResponse("error", "Failed to fetch order: " + e.getMessage(), null));
        }
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<AllOrderResponseDTO>> getAllOrders() {
        List<AllOrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Update order (PUT)
    @PutMapping("/update-order/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long orderId,
                                                     @RequestBody UpdateOrderRequest request) {
        try {
            logger.info("Updating order with id: {}", orderId);
            OrderResponse response = orderService.updateOrder(orderId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Order not found with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OrderResponse("error", e.getMessage(), null));
        } catch (Exception e) {
            logger.error("Error updating order with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OrderResponse("error", "Failed to update order: " + e.getMessage(), null));
        }
    }

    // Patch order (PATCH)
    @PatchMapping("/patch-order/{orderId}")
    public ResponseEntity<OrderResponse> patchOrder(@PathVariable Long orderId,
                                                    @RequestBody Map<String, Object> updates) {
        try {
            logger.info("Patching order with id: {}", orderId);
            OrderResponse response = orderService.patchOrder(orderId, updates);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Order not found with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OrderResponse("error", e.getMessage(), null));
        } catch (Exception e) {
            logger.error("Error patching order with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OrderResponse("error", "Failed to patch order: " + e.getMessage(), null));
        }
    }

    // Delete order
    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Long orderId) {
        try {
            logger.info("Deleting order with id: {}", orderId);
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok(Map.of("message", "Order deleted successfully", "orderId", orderId.toString()));
        } catch (RuntimeException e) {
            logger.error("Order not found with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error deleting order with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete order: " + e.getMessage()));
        }
    }

    // Get orders by user ID
    @GetMapping("/get-by-userId/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        try {
            logger.info("Fetching orders for user id: {}", userId);
            List<OrderResponse> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders for user id: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get orders by status
    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable String orderStatus) {
        try {
            logger.info("Fetching orders by status: {}", orderStatus);
            List<OrderResponse> orders = orderService.getOrdersByStatus(orderStatus);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders by status: {}", orderStatus, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get orders by product ID
    @GetMapping("/get-by-productId/{productId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByProductId(@PathVariable Long productId) {
        try {
            logger.info("Fetching orders by product id: {}", productId);
            List<OrderResponse> orders = orderService.getOrdersByProductId(productId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders by product id: {}", productId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get orders by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<OrderResponse>> getOrdersByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            logger.info("Fetching orders by date range: {} to {}", startDate, endDate);
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            List<OrderResponse> orders = orderService.getOrdersByDateRange(start, end);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders by date range: {} to {}", startDate, endDate, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Test Shiprocket connection
     */
    @GetMapping("/test-shiprocket")
    public ResponseEntity<Map<String, String>> testShiprocketConnection() {
        try {
            logger.info("Testing Shiprocket connection");
            String result = shiprocketService.testConnection();
            return ResponseEntity.ok(Map.of("status", "success", "message", result));
        } catch (Exception e) {
            logger.error("Error testing Shiprocket connection", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Failed to connect to Shiprocket: " + e.getMessage()));
        }
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long orderId) {
        try {
            logger.info("Canceling order with ID: {}", orderId);
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok(Map.of("message", "Order canceled successfully", "orderId", orderId.toString()));
        } catch (RuntimeException e) {
            logger.error("Error canceling order with ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error canceling order with ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to cancel order: " + e.getMessage()));
        }
    }

    // Cancel order by shiprocketOrderId (new endpoint for testing)
    @PostMapping("/cancel/shiprocket/{shiprocketOrderId}")
    public ResponseEntity<Map<String, String>> cancelOrderByShiprocketId(@PathVariable String shiprocketOrderId) {
        try {
            logger.info("Canceling order with Shiprocket order ID: {}", shiprocketOrderId);
            orderService.cancelOrderByShiprocketId(shiprocketOrderId);
            return ResponseEntity.ok(Map.of("message", "Order canceled successfully", "shiprocketOrderId", shiprocketOrderId));
        } catch (RuntimeException e) {
            logger.error("Order not found with Shiprocket order ID: {}", shiprocketOrderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error canceling order with Shiprocket order ID: {}", shiprocketOrderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to cancel order: " + e.getMessage()));
        }
    }

    @PostMapping("/return/{orderId}")
    public ResponseEntity<Map<String, String>> createReturnOrder(@PathVariable Long orderId,
                                                                 @RequestBody ReturnRequestDTO returnRequest) {
        try {
            logger.info("Creating return for order with id: {}", orderId);
            String returnId = orderService.createReturnOrder(orderId, returnRequest);
            return ResponseEntity.ok(Map.of("message", "Return order created successfully",
                    "orderId", orderId.toString(),
                    "returnId", returnId));
        } catch (RuntimeException e) {
            logger.error("Order not found with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error creating return for order with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create return order: " + e.getMessage()));
        }
    }

    @PostMapping("/exchange/{orderId}")
    public ResponseEntity<Map<String, String>> createExchangeOrder(@PathVariable Long orderId,
                                                                   @RequestBody ExchangeRequestDTO exchangeRequest) {
        try {
            logger.info("Creating exchange for order with id: {}", orderId);
            String exchangeId = orderService.createExchangeOrder(orderId, exchangeRequest);
            return ResponseEntity.ok(Map.of("message", "Exchange order created successfully",
                    "orderId", orderId.toString(),
                    "exchangeId", exchangeId));
        } catch (RuntimeException e) {
            logger.error("Order not found with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error creating exchange for order with id: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create exchange order: " + e.getMessage()));
        }
    }
}