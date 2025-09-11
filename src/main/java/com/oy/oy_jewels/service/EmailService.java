package com.oy.oy_jewels.service;

import java.math.BigDecimal;
import java.util.List;

public interface EmailService {
    void sendOtpEmail(String toEmail, String otp);

    public void sendOrderConfirmationEmail(String toEmail, String customerName, String orderId,
                                           BigDecimal totalAmount, List<String> productNames, String mobileNumber);
}
