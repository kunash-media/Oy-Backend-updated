package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.CouponDataDto;
import com.oy.oy_jewels.entity.CouponEntity;
import com.oy.oy_jewels.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    // COMPLETE CORRECTED CONTROLLER - Replace your existing controller method with this
    @PostMapping(value = "/create-coupon", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCoupon(
            @RequestPart(value = "couponBanner", required = false) MultipartFile couponBanner,
            @RequestPart("couponData") CouponDataDto couponData) {

        try {
            CouponEntity coupon = couponService.createCoupon(
                    couponBanner,
                    couponData.getCouponName(),
                    couponData.getCouponCode(),
                    couponData.getValidDate(),
                    couponData.getValidUntil(),
                    couponData.getDiscountType(),
                    couponData.getDiscountValue(),
                    couponData.getDescription()
            );
            return ResponseEntity.ok(coupon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create coupon: " + e.getMessage()));
        }
    }

    @GetMapping("/Get-All-Coupons")
    public ResponseEntity<List<CouponEntity>> getAllCoupons() {
        List<CouponEntity> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponEntity> getCouponById(@PathVariable Long id) {
        CouponEntity coupon = couponService.getCouponById(id);
        if (coupon != null) {
            return ResponseEntity.ok(coupon);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/code/{couponCode}")
    public ResponseEntity<CouponEntity> getCouponByCode(@PathVariable String couponCode) {
        CouponEntity coupon = couponService.getCouponByCode(couponCode);
        if (coupon != null) {
            return ResponseEntity.ok(coupon);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponEntity> updateCoupon(
            @PathVariable Long id,
            @RequestPart(value = "couponBanner", required = false) MultipartFile couponBanner,
            @RequestPart("couponName") String couponName,
            @RequestPart("couponCode") String couponCode,
            @RequestPart("validDate") String validDate,
            @RequestPart("validUntil") String validUntil,
            @RequestPart("discountType") String discountType,
            @RequestPart("discountValue") Double discountValue,
            @RequestPart(value = "description", required = false) String description) {

        CouponEntity updatedCoupon = couponService.updateCoupon(id, couponBanner, couponName,
                couponCode, validDate, validUntil, discountType, discountValue, description);

        if (updatedCoupon != null) {
            return ResponseEntity.ok(updatedCoupon);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok().build();
    }
}
