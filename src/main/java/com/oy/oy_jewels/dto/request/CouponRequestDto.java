package com.oy.oy_jewels.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public class CouponRequestDto {

    private String couponDescription;
    private String couponType;
    private String couponDiscount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String validFromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String validUntilDate;

    private String couponCode;
    private String status;
    private Boolean isUsed;
    private Long userId;
    private List<Long> userIds;

    // Constructors
    public CouponRequestDto() {
    }

    public CouponRequestDto(String couponDescription, String couponType, String couponDiscount,
                            String validFromDate, String validUntilDate, String couponCode, Long userId, List<Long> userIds) {
        this.couponDescription = couponDescription;
        this.couponType = couponType;
        this.couponDiscount = couponDiscount;
        this.validFromDate = validFromDate;
        this.validUntilDate = validUntilDate;
        this.couponCode = couponCode;
        this.userId = userId;
        this.userIds = userIds;
    }

    // Getters and Setters
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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}