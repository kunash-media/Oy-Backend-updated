package com.oy.oy_jewels.dto.response;

public class ShiprocketOrderResponse {
    private String order_id;
    private String shipment_id;
    private String status;
    private String status_code;
    private String onboarding_completed_now;
    private String awb_code;
    private String courier_company_id;
    private String courier_name;

    // Getters and Setters
    public String getOrder_id() { return order_id; }
    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getShipment_id() { return shipment_id; }
    public void setShipment_id(String shipment_id) { this.shipment_id = shipment_id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStatus_code() { return status_code; }
    public void setStatus_code(String status_code) { this.status_code = status_code; }

    public String getOnboarding_completed_now() { return onboarding_completed_now; }
    public void setOnboarding_completed_now(String onboarding_completed_now) { this.onboarding_completed_now = onboarding_completed_now; }

    public String getAwb_code() { return awb_code; }
    public void setAwb_code(String awb_code) { this.awb_code = awb_code; }

    public String getCourier_company_id() { return courier_company_id; }
    public void setCourier_company_id(String courier_company_id) { this.courier_company_id = courier_company_id; }

    public String getCourier_name() { return courier_name; }
    public void setCourier_name(String courier_name) { this.courier_name = courier_name; }
}