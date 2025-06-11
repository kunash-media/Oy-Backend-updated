package com.oy.oy_jewels.dto.response;

import com.oy.oy_jewels.entity.OrderItemEntity;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long productId;
    private String name;
    private Integer productQuantity;
    private BigDecimal productPrice;
    private BigDecimal subtotal;

    // Constructor from OrderItemEntity
    public OrderItemResponse(OrderItemEntity orderItem) {
        this.productId = orderItem.getProduct().getProductId();
        this.name = orderItem.getProduct().getProductTitle();
        this.productQuantity = orderItem.getQuantity();
        this.productPrice = orderItem.getProductPrice();
        this.subtotal = orderItem.getSubtotal();
    }

    // Constructors
    public OrderItemResponse() {}

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getProductQuantity() { return productQuantity; }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}