package com.oy.oy_jewels.service;

import java.util.List;

public interface WhatsAppService {
    void sendOrderConfirmationMessage(String userPhone, String userName, String orderId,
                                      String orderAmount,
                                      List<String> productNames, String templateName);

    public void testWithRealData();

    void sendBirthdayCouponMessage(String userPhone, String userName, String couponDiscount,
                                   List<String> categories, String couponCode, String validUntilDate);
}