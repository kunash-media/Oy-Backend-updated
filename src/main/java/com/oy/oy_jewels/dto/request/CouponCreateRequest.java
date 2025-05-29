package com.oy.oy_jewels.dto.request;

// DTO for JSON requests
public class CouponCreateRequest {
    private String couponName;
    private String couponCode;
    private String validDate;
    private String validUntil;
    private String discountType;
    private Double discountValue;
    private String description;

    // Constructors, getters, and setters
    public CouponCreateRequest() {}

    public String getCouponName() { return couponName; }
    public void setCouponName(String couponName) { this.couponName = couponName; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public String getValidDate() { return validDate; }
    public void setValidDate(String validDate) { this.validDate = validDate; }

    public String getValidUntil() { return validUntil; }
    public void setValidUntil(String validUntil) { this.validUntil = validUntil; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
