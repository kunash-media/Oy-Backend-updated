package com.oy.oy_jewels.dto.request;

public class CouponDataDto {

    private String couponName;
    private String couponCode;
    private String validDate;
    private String validUntil;
    private String discountType;
    private Double discountValue;
    private String description;

    // Constructors
    public CouponDataDto() {}

    public CouponDataDto(String couponName, String couponCode, String validDate,
                         String validUntil, String discountType, Double discountValue,
                         String description) {
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.validDate = validDate;
        this.validUntil = validUntil;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.description = description;
    }

    // Getters and Setters
    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
