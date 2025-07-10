package com.oy.oy_jewels.service;


import com.oy.oy_jewels.dto.request.PaymentRequest;
import com.oy.oy_jewels.dto.request.PaymentVerificationRequest;
import com.oy.oy_jewels.dto.response.PaymentResponse;
import com.oy.oy_jewels.dto.response.PaymentVerificationResponse;
import com.oy.oy_jewels.entity.PaymentOrder;

import java.util.List;

public interface PaymentService {

    /**
     * Create a new payment order
     */
    PaymentResponse createPaymentOrder(PaymentRequest request) throws Exception;

    /**
     * Verify payment signature
     */
    PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request) throws Exception;

    /**
     * Get payment order by Razorpay order ID
     */
    PaymentOrder getPaymentOrderByRazorpayId(String razorpayOrderId);

    /**
     * Get all payment orders
     */
    List<PaymentOrder> getAllPaymentOrders();

    /**
     * Update payment status
     */
    PaymentOrder updatePaymentStatus(String razorpayOrderId, String status);
}