package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateOrderRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal productPrice;
    private BigDecimal totalAmount;
    private String paymentMode;
    private String orderStatus;
    private LocalDate deliveryDate;
    private LocalDate orderDate;

    // Constructors
    public CreateOrderRequest() {}

    public CreateOrderRequest(Long userId, Long productId, Integer quantity, BigDecimal productPrice,
                              BigDecimal totalAmount, String paymentMode, String orderStatus,
                              LocalDate deliveryDate, LocalDate orderDate) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.totalAmount = totalAmount;
        this.paymentMode = paymentMode;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

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

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
}