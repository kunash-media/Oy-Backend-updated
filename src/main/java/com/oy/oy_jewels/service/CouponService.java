package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.CouponRequestDto;
import com.oy.oy_jewels.dto.response.CouponResponseDto;
import com.oy.oy_jewels.dto.response.UserCouponsResponseDto;

import java.util.List;

public interface CouponService {

    CouponResponseDto createCoupon(CouponRequestDto couponRequestDto);

    CouponResponseDto getCouponById(Long couponId);

    List<CouponResponseDto> getAllCoupons();

    CouponResponseDto updateCoupon(Long couponId, CouponRequestDto couponRequestDto);

    void deleteCoupon(Long couponId);

    CouponResponseDto getCouponByCode(String couponCode);

    List<CouponResponseDto> getCouponsByType(String couponType);

    List<CouponResponseDto> getAvailableCoupons();

    CouponResponseDto applyCoupon(String couponCode);

    CouponResponseDto validateCoupon(String couponCode);

    List<CouponResponseDto> bulkCreateCoupons(CouponRequestDto couponRequestDto, List<Long> userIds);

    UserCouponsResponseDto getCouponsByUserId(Long userId);
}