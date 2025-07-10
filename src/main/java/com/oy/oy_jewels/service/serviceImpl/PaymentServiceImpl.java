package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.PaymentRequest;
import com.oy.oy_jewels.dto.request.PaymentVerificationRequest;
import com.oy.oy_jewels.dto.response.PaymentResponse;
import com.oy.oy_jewels.dto.response.PaymentVerificationResponse;
import com.oy.oy_jewels.entity.PaymentOrder;
import com.oy.oy_jewels.enum_const.PaymentStatus;
import com.oy.oy_jewels.repository.PaymentOrderRepository;
import com.oy.oy_jewels.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${razorpay.key_id:}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret:}")
    private String razorpayKeySecret;

    private RazorpayClient razorpayClient;

    // Initialize Razorpay client
    private RazorpayClient getRazorpayClient() throws RazorpayException {
        if (razorpayClient == null) {
            razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        }
        return razorpayClient;
    }

    @Override
    public PaymentResponse createPaymentOrder(PaymentRequest request) throws Exception {

        try {
            // Convert amount to paise for Razorpay
            long amountInPaise = Math.round(request.getAmount() * 100);

            // Validate minimum amount (Razorpay minimum is typically 100 paise = ₹1)
            if(amountInPaise < 100) {
                throw new Exception("Amount must be at least ₹1");
            }

            // Create Razorpay order
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);    // Convert to paise
            orderRequest.put("currency", request.getCurrency());
            orderRequest.put("receipt", request.getReceipt());

            // Add customer notes
            JSONObject notes = new JSONObject();
            notes.put("customer_name", request.getCustomerName());
            notes.put("customer_email", request.getCustomerEmail());
            notes.put("customer_phone", request.getCustomerPhone());
            orderRequest.put("notes", notes);

            Order razorpayOrder = getRazorpayClient().orders.create(orderRequest);

            // Save to database
            PaymentOrder paymentOrder = new PaymentOrder();

            paymentOrder.setRazorpayOrderId(razorpayOrder.get("id"));

            paymentOrder.setAmount(request.getAmount());      // Store in paise
            paymentOrder.setCurrency(request.getCurrency());
            paymentOrder.setReceipt(request.getReceipt());
            paymentOrder.setCustomerName(request.getCustomerName());
            paymentOrder.setCustomerEmail(request.getCustomerEmail());
            paymentOrder.setCustomerPhone(request.getCustomerPhone());
            paymentOrder.setStatus(PaymentStatus.CREATED);

            paymentOrderRepository.save(paymentOrder);

            // Create response
            PaymentResponse response = new PaymentResponse();
            response.setRazorpayOrderId(razorpayOrder.get("id"));
            response.setAmount(request.getAmount());
            response.setCurrency(request.getCurrency());
            response.setReceipt(request.getReceipt());
            response.setStatus(razorpayOrder.get("status"));
            response.setCustomerName(request.getCustomerName());
            response.setCustomerEmail(request.getCustomerEmail());
            response.setCustomerPhone(request.getCustomerPhone());
            response.setRazorpayKeyId(razorpayKeyId);

            log.info("Payment order created successfully: {}", (Object) razorpayOrder.get("id"));
            return response;

        } catch (RazorpayException e) {
            log.error("Error creating Razorpay order: {}", e.getMessage());
            throw new Exception("Failed to create payment order: " + e.getMessage());
        }
    }

    @Override
    public PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request) throws Exception {
        try {
            // Get payment order from database
            PaymentOrder paymentOrder = paymentOrderRepository
                    .findByRazorpayOrderId(request.getRazorpayOrderId())
                    .orElseThrow(() -> new Exception("Payment order not found"));

            // Verify signature
            boolean isValidSignature = verifyRazorpaySignature(
                    request.getRazorpayOrderId(),
                    request.getRazorpayPaymentId(),
                    request.getRazorpaySignature()
            );

            PaymentVerificationResponse response = new PaymentVerificationResponse();

            if (isValidSignature) {
                // Update payment order
                paymentOrder.setRazorpayPaymentId(request.getRazorpayPaymentId());
                paymentOrder.setRazorpaySignature(request.getRazorpaySignature());
                paymentOrder.setStatus(PaymentStatus.PAID);
                paymentOrderRepository.save(paymentOrder);

                response.setSuccess(true);
                response.setMessage("Payment verified successfully");
                response.setStatus("PAID");

                log.info("Payment verified successfully: {}", request.getRazorpayPaymentId());
            } else {
                paymentOrder.setStatus(PaymentStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);

                response.setSuccess(false);
                response.setMessage("Payment verification failed");
                response.setStatus("FAILED");

                log.error("Payment verification failed for order: {}", request.getRazorpayOrderId());
            }

            response.setPaymentId(request.getRazorpayPaymentId());
            response.setOrderId(request.getRazorpayOrderId());

            return response;

        } catch (Exception e) {
            log.error("Error verifying payment: {}", e.getMessage());
            throw new Exception("Payment verification failed: " + e.getMessage());
        }
    }

    private boolean verifyRazorpaySignature(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;
            String expectedSignature = calculateHmacSha256(payload, razorpayKeySecret);
            return signature.equals(expectedSignature);
        } catch (Exception e) {
            log.error("Error verifying signature: {}", e.getMessage());
            return false;
        }
    }

    private String calculateHmacSha256(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public PaymentOrder getPaymentOrderByRazorpayId(String razorpayOrderId) {
        return paymentOrderRepository.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Payment order not found: " + razorpayOrderId));
    }

    @Override
    public List<PaymentOrder> getAllPaymentOrders() {
        return paymentOrderRepository.findAll();
    }

    @Override
    public PaymentOrder updatePaymentStatus(String razorpayOrderId, String status) {
        PaymentOrder paymentOrder = getPaymentOrderByRazorpayId(razorpayOrderId);
        paymentOrder.setStatus(PaymentStatus.valueOf(status.toUpperCase()));
        return paymentOrderRepository.save(paymentOrder);
    }
}