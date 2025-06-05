package com.oy.oy_jewels.dto.request;

public class CheckOutRequestDTO {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String streetAddress;
    private String city;
    private String country;
    private String zipPostalCode;
    private String orderSummaryTitle;
    private String couponCode;
    private String subtotalLabel;
    private String shippingLabel;
    private String taxLabel;
    private String totalCostLabel;

    // Default constructor
    public CheckOutRequestDTO() {}

    // Parameterized constructor
    public CheckOutRequestDTO(String fullName, String email, String phoneNumber, String alternatePhoneNumber,
                              String streetAddress, String city, String country, String zipPostalCode,
                              String orderSummaryTitle, String couponCode, String subtotalLabel,
                              String shippingLabel, String taxLabel, String totalCostLabel) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.alternatePhoneNumber = alternatePhoneNumber;
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
        this.zipPostalCode = zipPostalCode;
        this.orderSummaryTitle = orderSummaryTitle;
        this.couponCode = couponCode;
        this.subtotalLabel = subtotalLabel;
        this.shippingLabel = shippingLabel;
        this.taxLabel = taxLabel;
        this.totalCostLabel = totalCostLabel;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getOrderSummaryTitle() {
        return orderSummaryTitle;
    }

    public void setOrderSummaryTitle(String orderSummaryTitle) {
        this.orderSummaryTitle = orderSummaryTitle;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getSubtotalLabel() {
        return subtotalLabel;
    }

    public void setSubtotalLabel(String subtotalLabel) {
        this.subtotalLabel = subtotalLabel;
    }

    public String getShippingLabel() {
        return shippingLabel;
    }

    public void setShippingLabel(String shippingLabel) {
        this.shippingLabel = shippingLabel;
    }

    public String getTaxLabel() {
        return taxLabel;
    }

    public void setTaxLabel(String taxLabel) {
        this.taxLabel = taxLabel;
    }

    public String getTotalCostLabel() {
        return totalCostLabel;
    }

    public void setTotalCostLabel(String totalCostLabel) {
        this.totalCostLabel = totalCostLabel;
    }
}

