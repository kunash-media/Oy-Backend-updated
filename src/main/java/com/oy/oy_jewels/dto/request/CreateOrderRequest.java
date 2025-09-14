package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrderRequest {
    private Long userId;
    private String shippingAddress;
    private String shippingCity;
    private String shippingState;
    private String shippingPincode;
    private String shippingCountry = "India";
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;
    private String paymentMethod;
    private List<OrderItemRequest> items;

    // Optional billing fields (if different from shipping)
    private String billingCustomerName;
    private String billingLastName;
    private String billingAddress;
    private String billingCity;
    private String billingPincode;
    private String billingState;
    private String billingCountry = "India";
    private String billingEmail;
    private String billingPhone;
    private Boolean shippingIsBilling = true;

    private String couponAppliedCode;

    private BigDecimal tax;
    private BigDecimal discountAmount; // New field for discount

    // Constructors
    public CreateOrderRequest() {}

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getShippingCity() { return shippingCity; }
    public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }

    public String getShippingState() { return shippingState; }
    public void setShippingState(String shippingState) { this.shippingState = shippingState; }

    public String getShippingPincode() { return shippingPincode; }
    public void setShippingPincode(String shippingPincode) { this.shippingPincode = shippingPincode; }

    public String getShippingCountry() { return shippingCountry; }
    public void setShippingCountry(String shippingCountry) { this.shippingCountry = shippingCountry; }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }

    public String getBillingCustomerName() { return billingCustomerName; }
    public void setBillingCustomerName(String billingCustomerName) { this.billingCustomerName = billingCustomerName; }

    public String getBillingLastName() { return billingLastName; }
    public void setBillingLastName(String billingLastName) { this.billingLastName = billingLastName; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public String getBillingCity() { return billingCity; }
    public void setBillingCity(String billingCity) { this.billingCity = billingCity; }

    public String getBillingPincode() { return billingPincode; }
    public void setBillingPincode(String billingPincode) { this.billingPincode = billingPincode; }

    public String getBillingState() { return billingState; }
    public void setBillingState(String billingState) { this.billingState = billingState; }

    public String getBillingCountry() { return billingCountry; }
    public void setBillingCountry(String billingCountry) { this.billingCountry = billingCountry; }

    public String getBillingEmail() { return billingEmail; }
    public void setBillingEmail(String billingEmail) { this.billingEmail = billingEmail; }

    public String getBillingPhone() { return billingPhone; }
    public void setBillingPhone(String billingPhone) { this.billingPhone = billingPhone; }

    public Boolean getShippingIsBilling() { return shippingIsBilling; }
    public void setShippingIsBilling(Boolean shippingIsBilling) { this.shippingIsBilling = shippingIsBilling; }


    public String getCouponAppliedCode() {
        return couponAppliedCode;
    }

    public void setCouponAppliedCode(String couponAppliedCode) {
        this.couponAppliedCode = couponAppliedCode;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
