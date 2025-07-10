package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.PaymentRequest;
import com.oy.oy_jewels.dto.request.PaymentVerificationRequest;
import com.oy.oy_jewels.dto.response.PaymentResponse;
import com.oy.oy_jewels.dto.response.PaymentVerificationResponse;
import com.oy.oy_jewels.entity.PaymentOrder;
import com.oy.oy_jewels.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Create a new payment order
     */
    @PostMapping("/create-order")
    public ResponseEntity<?> createPaymentOrder(@RequestBody PaymentRequest request) {
        try {
            log.info("Creating payment order for amount: {}", request.getAmount());
            PaymentResponse response = paymentService.createPaymentOrder(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error("Error creating payment order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating payment order: " + e.getMessage());
        }
    }

    /**
     * Verify payment after successful payment
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment( @RequestBody PaymentVerificationRequest request) {
        try {
            log.info("Verifying payment for order: {}", request.getRazorpayOrderId());
            PaymentVerificationResponse response = paymentService.verifyPayment(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error verifying payment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error verifying payment: " + e.getMessage());
        }
    }

    /**
     * Get payment order by Razorpay order ID
     */
    @GetMapping("/order/{razorpayOrderId}")
    public ResponseEntity<?> getPaymentOrder(@PathVariable String razorpayOrderId) {
        try {
            PaymentOrder paymentOrder = paymentService.getPaymentOrderByRazorpayId(razorpayOrderId);
            return ResponseEntity.ok(paymentOrder);
        } catch (Exception e) {
            log.error("Error fetching payment order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Payment order not found: " + e.getMessage());
        }
    }

    /**
     * Get all payment orders
     */
    @GetMapping("/orders")
    public ResponseEntity<List<PaymentOrder>> getAllPaymentOrders() {
        try {
            List<PaymentOrder> orders = paymentService.getAllPaymentOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error fetching payment orders: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update payment status
     */
    @PutMapping("/update-status/{razorpayOrderId}")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable String razorpayOrderId,
            @RequestParam String status) {
        try {
            PaymentOrder updatedOrder = paymentService.updatePaymentStatus(razorpayOrderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            log.error("Error updating payment status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating payment status: " + e.getMessage());
        }
    }
}
