package com.oy.oy_jewels.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShiprocketOrderResponse {
    private String order_id;
    private String shipment_id;
    private String status;
    private String message;
    private Integer onboarding_completed_now;
    private Boolean success;

    // Constructors, getters, and setters
    public ShiprocketOrderResponse() {}

    public String getOrder_id() { return order_id; }
    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getShipment_id() { return shipment_id; }
    public void setShipment_id(String shipment_id) { this.shipment_id = shipment_id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getOnboarding_completed_now() { return onboarding_completed_now; }
    public void setOnboarding_completed_now(Integer onboarding_completed_now) { this.onboarding_completed_now = onboarding_completed_now; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
}