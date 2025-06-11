package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateOrderRequest {
    private Long userId;
    private String shippingAddress;
    private String paymentMethod;
    private List<OrderItemRequest> items;

    // Constructors
    public CreateOrderRequest() {}

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}