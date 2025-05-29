package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.CouponEntity;
import com.oy.oy_jewels.repository.CouponRepository;
import com.oy.oy_jewels.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    private static final String UPLOAD_DIR = "uploads/coupons/";

    @Override
    public CouponEntity createCoupon(MultipartFile couponBanner, String couponName, String couponCode,
                                     String validDate, String validUntil, String discountType,
                                     Double discountValue, String description) {
        byte[] bannerData = null;
        if (couponBanner != null && !couponBanner.isEmpty()) {
            try {
                bannerData = couponBanner.getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Failed to process coupon banner image", e);
            }
        }

        CouponEntity coupon = new CouponEntity();
        coupon.setCouponBanner(bannerData);
        coupon.setCouponName(couponName);
        coupon.setCouponCode(couponCode);
        coupon.setValidDate(LocalDate.parse(validDate));
        coupon.setValidUntil(LocalDate.parse(validUntil));
        coupon.setDiscountType(discountType);
        coupon.setDiscountValue(discountValue);
        coupon.setDescription(description);

        return couponRepository.save(coupon);
    }

    @Override
    public List<CouponEntity> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public CouponEntity getCouponById(Long id) {
        return couponRepository.findById(id).orElse(null);
    }

    @Override
    public CouponEntity getCouponByCode(String couponCode) {
        return couponRepository.findByCouponCode(couponCode).orElse(null);
    }

    @Override
    public CouponEntity updateCoupon(Long id, MultipartFile couponBanner, String couponName,
                                     String couponCode, String validDate, String validUntil,
                                     String discountType, Double discountValue, String description) {

        CouponEntity existingCoupon = couponRepository.findById(id).orElse(null);
        if (existingCoupon == null) {
            return null;
        }

//        if (couponBanner != null && !couponBanner.isEmpty()) {
//            String bannerPath = saveFile(couponBanner);
//            existingCoupon.setCouponBanner(bannerPath);
//        }

        existingCoupon.setCouponName(couponName);
        existingCoupon.setCouponCode(couponCode);
        existingCoupon.setValidDate(LocalDate.parse(validDate));
        existingCoupon.setValidUntil(LocalDate.parse(validUntil));
        existingCoupon.setDiscountType(discountType);
        existingCoupon.setDiscountValue(discountValue);
        existingCoupon.setDescription(description);

        return couponRepository.save(existingCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    private String saveFile(MultipartFile file) {
        try {
            // Create directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Save file
            Files.copy(file.getInputStream(), filePath);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }
}