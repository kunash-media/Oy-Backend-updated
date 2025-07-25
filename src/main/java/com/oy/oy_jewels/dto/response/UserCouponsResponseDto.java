package com.oy.oy_jewels.dto.response;

import java.util.List;

public class UserCouponsResponseDto {

    private Long userId;
    private List<CouponResponseDto> userCoupons;
    private int totalValidCoupons;
    private int totalExpiredCoupons;

    // Constructors
    public UserCouponsResponseDto() {
    }

    public UserCouponsResponseDto(Long userId, List<CouponResponseDto> userCoupons, int totalValidCoupons, int totalExpiredCoupons) {
        this.userId = userId;
        this.userCoupons = userCoupons;
        this.totalValidCoupons = totalValidCoupons;
        this.totalExpiredCoupons = totalExpiredCoupons;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CouponResponseDto> getUserCoupons() {
        return userCoupons;
    }

    public void setUserCoupons(List<CouponResponseDto> userCoupons) {
        this.userCoupons = userCoupons;
    }

    public int getTotalValidCoupons() {
        return totalValidCoupons;
    }

    public void setTotalValidCoupons(int totalValidCoupons) {
        this.totalValidCoupons = totalValidCoupons;
    }

    public int getTotalExpiredCoupons() {
        return totalExpiredCoupons;
    }

    public void setTotalExpiredCoupons(int totalExpiredCoupons) {
        this.totalExpiredCoupons = totalExpiredCoupons;
    }
}

