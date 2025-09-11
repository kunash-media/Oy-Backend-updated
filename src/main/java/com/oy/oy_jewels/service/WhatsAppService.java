package com.oy.oy_jewels.service;

import java.util.List;

public interface WhatsAppService {
    void sendOrderConfirmationMessage(String userPhone, String userName, String orderId, String orderAmount, List<String> productNames, String templateName);
    void sendBirthdayCouponMessage(String userPhone, String userFirstName, String couponDiscount, List<String> categories, String couponCode, String validUntilDate);
    void sendAnniversaryCouponMessage(String userPhone, String userFirstName, String couponDiscount, List<String> categories, String couponCode, String validUntilDate);
    void sendFestivalCouponMessage(String userPhone, String userFirstName, String couponDiscount, List<String> categories, String couponCode, String validUntilDate, String festivalName);
    void testWithRealData();

    void sendOtpMessage(String userPhone, String userName, String otp);
}