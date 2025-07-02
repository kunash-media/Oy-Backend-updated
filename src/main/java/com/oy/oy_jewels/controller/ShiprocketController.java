package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.response.ShiprocketOrderResponse;
import com.oy.oy_jewels.entity.OrderEntity;
import com.oy.oy_jewels.repository.OrderRepository;
import com.oy.oy_jewels.service.ShiprocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shiprocket")
public class ShiprocketController {

    private final ShiprocketService shiprocketService;
    private final OrderRepository orderRepository;

    @Autowired
    public ShiprocketController(ShiprocketService shiprocketService, OrderRepository orderRepository) {
        this.shiprocketService = shiprocketService;
        this.orderRepository = orderRepository;
    }

    /**
     * Create shipment for an existing order
     */
    @PostMapping("/create-shipment/{orderId}")
    public ResponseEntity<?> createShipment(@PathVariable Long orderId) {
        try {
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            if (order.getShiprocketOrderId() != null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Shipment already created for this order", null));
            }

            ShiprocketOrderResponse response = shiprocketService.createShiprocketOrder(order);

            return ResponseEntity.ok(new ApiResponse(true, "Shipment created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error creating shipment: " + e.getMessage(), null));
        }
    }

    /**
     * Get tracking details for an order
     */
    @GetMapping("/track/{orderId}")
    public ResponseEntity<?> trackOrder(@PathVariable Long orderId) {
        try {
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            if (order.getAwbCode() == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "No AWB code found for this order", null));
            }

            String trackingDetails = shiprocketService.getTrackingDetails(order.getAwbCode());

            return ResponseEntity.ok(new ApiResponse(true, "Tracking details retrieved", trackingDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error getting tracking details: " + e.getMessage(), null));
        }
    }

    /**
     * Cancel shipment
     */
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelShipment(@PathVariable Long orderId) {
        try {
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            if (order.getShiprocketOrderId() == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "No Shiprocket order found for this order", null));
            }

            boolean cancelled = shiprocketService.cancelShiprocketOrder(order.getShiprocketOrderId());

            if (cancelled) {
                order.setOrderStatus("cancelled");
                orderRepository.save(order);
                return ResponseEntity.ok(new ApiResponse(true, "Shipment cancelled successfully", null));
            } else {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Failed to cancel shipment", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error cancelling shipment: " + e.getMessage(), null));
        }
    }

    /**
     * Check courier serviceability
     */
    @GetMapping("/serviceability")
    public ResponseEntity<?> checkServiceability(
            @RequestParam String pickupPincode,
            @RequestParam String deliveryPincode,
            @RequestParam(defaultValue = "1.0") double weight) {
        try {
            String serviceability = shiprocketService.getAvailableCouriers(pickupPincode, deliveryPincode, weight);

            return ResponseEntity.ok(new ApiResponse(true, "Serviceability checked", serviceability));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error checking serviceability: " + e.getMessage(), null));
        }
    }

    /**
     * Webhook endpoint for Shiprocket status updates
     */
    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String payload) {
        try {
            // Process webhook payload
            // You can parse the JSON and update order status accordingly
            System.out.println("Received webhook: " + payload);

            // Example: Parse JSON and update order status
            // ObjectMapper mapper = new ObjectMapper();
            // JsonNode webhookData = mapper.readTree(payload);
            // String orderId = webhookData.get("order_id").asText();
            // String status = webhookData.get("current_status").asText();

            // Update order status in database based on webhook data

            return ResponseEntity.ok("Webhook processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing webhook: " + e.getMessage());
        }
    }


    static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;

        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        // Getters and Setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }

}