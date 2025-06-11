package com.oy.oy_jewels.dto.request;

public class OrderItemRequest {
    private Long productId;
    private Integer productQuantity;

    // Constructors
    public OrderItemRequest() {}

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getProductQuantity() { return productQuantity; }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }
}
