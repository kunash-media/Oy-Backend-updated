package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateOrderRequest {
    private Integer quantity;
    private BigDecimal productPrice;
    private BigDecimal totalAmount;
    private String paymentMode;
    private String orderStatus;
    private LocalDate deliveryDate;

    // Constructors
    public UpdateOrderRequest() {}

    // Getters and Setters
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
}

