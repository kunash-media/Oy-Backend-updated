package com.oy.oy_jewels.service;


import com.oy.oy_jewels.entity.CouponEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface CouponService {

    CouponEntity createCoupon(MultipartFile couponBanner, String couponName, String couponCode,
                              String validDate, String validUntil, String discountType,
                              Double discountValue, String description);

    List<CouponEntity> getAllCoupons();

    CouponEntity getCouponById(Long id);

    CouponEntity getCouponByCode(String couponCode);

    CouponEntity updateCoupon(Long id, MultipartFile couponBanner, String couponName,
                              String couponCode, String validDate, String validUntil,
                              String discountType, Double discountValue, String description);

    void deleteCoupon(Long id);
}
