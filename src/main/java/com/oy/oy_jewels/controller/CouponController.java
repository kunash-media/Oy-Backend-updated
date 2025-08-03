package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.CouponRequestDto;
import com.oy.oy_jewels.dto.response.CouponResponseDto;
import com.oy.oy_jewels.dto.response.UserCouponsResponseDto;
import com.oy.oy_jewels.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    // Create a new coupon
    @PostMapping("/create-Coupon")
    public ResponseEntity<CouponResponseDto> createCoupon(@RequestBody CouponRequestDto couponRequestDto) {
        try {
            CouponResponseDto createdCoupon = couponService.createCoupon(couponRequestDto);
            return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all coupons
    @GetMapping("/get-All-Coupons")
    public ResponseEntity<List<CouponResponseDto>> getAllCoupons() {
        List<CouponResponseDto> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    // Get coupon by ID
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponseDto> getCouponById(@PathVariable Long couponId) {
        try {
            CouponResponseDto coupon = couponService.getCouponById(couponId);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update coupon
    @PutMapping("/{couponId}")
    public ResponseEntity<CouponResponseDto> updateCoupon(@PathVariable Long couponId,
                                                          @RequestBody CouponRequestDto couponRequestDto) {
        try {
            CouponResponseDto updatedCoupon = couponService.updateCoupon(couponId, couponRequestDto);
            return new ResponseEntity<>(updatedCoupon, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete coupon
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Map<String, String>> deleteCoupon(@PathVariable Long couponId) {
        try {
            couponService.deleteCoupon(couponId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Coupon deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Coupon not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get coupon by code
    @GetMapping("/code/{couponCode}")
    public ResponseEntity<CouponResponseDto> getCouponByCode(@PathVariable String couponCode) {
        try {
            CouponResponseDto coupon = couponService.getCouponByCode(couponCode);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get available coupons
    @GetMapping("/available")
    public ResponseEntity<List<CouponResponseDto>> getAvailableCoupons() {
        List<CouponResponseDto> coupons = couponService.getAvailableCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    // Validate coupon
    @PostMapping("/validate/{couponCode}")
    public ResponseEntity<Map<String, Object>> validateCoupon(@PathVariable String couponCode) {
        try {
            CouponResponseDto coupon = couponService.validateCoupon(couponCode);
            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("coupon", coupon);
            response.put("message", "Coupon is valid and can be used");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Apply coupon
    @PostMapping("/apply/{couponCode}")
    public ResponseEntity<Map<String, Object>> applyCoupon(@PathVariable String couponCode) {
        try {
            CouponResponseDto coupon = couponService.applyCoupon(couponCode);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("coupon", coupon);
            response.put("message", "Coupon applied successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //unselect the coupon
    @PostMapping("/unselect/{couponCode}")
    public ResponseEntity<Map<String, Object>> unselectCoupon(@PathVariable String couponCode) {
        try {
            CouponResponseDto coupon = couponService.unselectCoupon(couponCode);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("coupon", coupon);
            response.put("message", "Coupon unselected successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Bulk create coupons
    @PostMapping("/bulk-create")
    public ResponseEntity<List<CouponResponseDto>> bulkCreateCoupons(@RequestBody CouponRequestDto couponRequestDto) {
        try {
            List<CouponResponseDto> createdCoupons = couponService.bulkCreateCoupons(couponRequestDto, couponRequestDto.getUserIds());
            return new ResponseEntity<>(createdCoupons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get coupons by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserCouponsResponseDto> getCouponsByUserId(@PathVariable Long userId) {
        try {
            UserCouponsResponseDto response = couponService.getCouponsByUserId(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}