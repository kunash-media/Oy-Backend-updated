package com.oy.oy_jewels.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CouponResponseDto {

    private Long couponId;
    private String couponDescription;
    private String couponType;
    private String couponDiscount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String validFromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String validUntilDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String createdAt;

    private String status;
    private String couponCode;
    private Boolean isUsed;
    private Long userId;

    // Constructors
    public CouponResponseDto() {
    }

    public CouponResponseDto(Long couponId, String couponDescription, String couponType,
                             String couponDiscount, String validFromDate, String validUntilDate,
                             String createdAt, String status, String couponCode, Boolean isUsed, Long userId) {
        this.couponId = couponId;
        this.couponDescription = couponDescription;
        this.couponType = couponType;
        this.couponDiscount = couponDiscount;
        this.validFromDate = validFromDate;
        this.validUntilDate = validUntilDate;
        this.createdAt = createdAt;
        this.status = status;
        this.couponCode = couponCode;
        this.isUsed = isUsed;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public String getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(String validFromDate) {
        this.validFromDate = validFromDate;
    }

    public String getValidUntilDate() {
        return validUntilDate;
    }

    public void setValidUntilDate(String validUntilDate) {
        this.validUntilDate = validUntilDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}