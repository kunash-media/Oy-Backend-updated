package com.oy.oy_jewels.dto.response;

import com.oy.oy_jewels.entity.OrderEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderResponse {
    private Long orderId;
    private Integer quantity;
    private BigDecimal productPrice;
    private BigDecimal totalAmount;
    private String paymentMode;
    private String orderStatus;
    private LocalDate deliveryDate;
    private LocalDate orderDate;

    // User information
    private Long userId;
    private String customerName;
    private String customerEmail;

    // Product information
    private Long productId;
    private String productName;
    private String productCategory;

    // Constructors
    public OrderResponse() {}

    public OrderResponse(OrderEntity order) {
        this.orderId = order.getOrderId();
        this.quantity = order.getQuantity();
        this.productPrice = order.getProductPrice();
        this.totalAmount = order.getTotalAmount();
        this.paymentMode = order.getPaymentMode();
        this.orderStatus = order.getOrderStatus();
        this.deliveryDate = order.getDeliveryDate();
        this.orderDate = order.getOrderDate();

        if (order.getUser() != null) {
            this.userId = order.getUser().getUserId();
            this.customerName = order.getUser().getCustomerName();
            this.customerEmail = order.getUser().getEmail();
        }

        if (order.getProduct() != null) {
            this.productId = order.getProduct().getProductId();
            this.productName = order.getProduct().getProductName();
            this.productCategory = order.getProduct().getCategory();
        }
    }

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

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

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }
}

