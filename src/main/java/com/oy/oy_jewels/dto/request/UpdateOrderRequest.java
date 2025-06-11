package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateOrderRequest {
    private String shippingAddress;
    private String paymentMethod;
    private String orderStatus;
    private LocalDate deliveryDate;

    // Constructors
    public UpdateOrderRequest() {}

    // Getters and Setters
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
}

