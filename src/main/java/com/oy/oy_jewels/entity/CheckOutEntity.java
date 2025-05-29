package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checkout")
public class CheckOutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "alternate_phone_number")
    private String alternatePhoneNumber;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_postal_code")
    private String zipPostalCode;

    @Column(name = "order_summary_title")
    private String orderSummaryTitle;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "subtotal_label")
    private String subtotalLabel;

    @Column(name = "shipping_label")
    private String shippingLabel;

    @Column(name = "tax_label")
    private String taxLabel;

    @Column(name = "total_cost_label")
    private String totalCostLabel;

    // Default constructor
    public CheckOutEntity() {}

    // Parameterized constructor
    public CheckOutEntity(String fullName, String email, String phoneNumber, String alternatePhoneNumber,
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

