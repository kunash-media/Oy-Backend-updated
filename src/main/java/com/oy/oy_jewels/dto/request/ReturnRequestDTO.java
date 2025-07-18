package com.oy.oy_jewels.dto.request;

import java.util.List;

public class ReturnRequestDTO {
    private List<String> orderItemIds;
    private String reason;

    // Constructors
    public ReturnRequestDTO() {}

    // Getters and Setters
    public List<String> getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(List<String> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}