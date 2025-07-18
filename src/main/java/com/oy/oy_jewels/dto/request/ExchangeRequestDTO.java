package com.oy.oy_jewels.dto.request;

import java.util.List;

public class ExchangeRequestDTO {
    private List<String> orderItemIds;
    private String newProductId;
    private String reason;

    // Constructors
    public ExchangeRequestDTO() {}

    // Getters and Setters
    public List<String> getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(List<String> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public String getNewProductId() {
        return newProductId;
    }

    public void setNewProductId(String newProductId) {
        this.newProductId = newProductId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
