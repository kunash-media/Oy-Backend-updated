package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.CouponRequestDto;
import com.oy.oy_jewels.dto.response.CouponResponseDto;
import com.oy.oy_jewels.dto.response.UserCouponsResponseDto;
import com.oy.oy_jewels.entity.CouponEntity;
import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.CouponRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public CouponResponseDto createCoupon(CouponRequestDto couponRequestDto) {
        UserEntity user = userRepository.findById(couponRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + couponRequestDto.getUserId()));

        if (couponRepository.existsByCouponCode(couponRequestDto.getCouponCode())) {
            throw new RuntimeException("Coupon code already exists: " + couponRequestDto.getCouponCode());
        }

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setEventType(couponRequestDto.getEventType());
        couponEntity.setCouponDescription(couponRequestDto.getCouponDescription());
        couponEntity.setCouponDiscount(couponRequestDto.getCouponDiscount());
        couponEntity.setValidFromDate(LocalDate.parse(couponRequestDto.getValidFromDate(), DATE_FORMATTER));
        couponEntity.setValidUntilDate(LocalDate.parse(couponRequestDto.getValidUntilDate(), DATE_FORMATTER));
        couponEntity.setCouponCode(couponRequestDto.getCouponCode());
        couponEntity.setCreatedAt(LocalDateTime.now());
        couponEntity.setStatus(couponRequestDto.getStatus() != null ? couponRequestDto.getStatus() : "valid");
        couponEntity.setIsUsed(couponRequestDto.getIsUsed() != null ? couponRequestDto.getIsUsed() : false);
        couponEntity.setUser(user);
        couponEntity.setCategory(couponRequestDto.getCategory());

        CouponEntity savedCoupon = couponRepository.save(couponEntity);
        return convertToResponseDto(savedCoupon);
    }

    @Override
    public CouponResponseDto getCouponById(Long couponId) {
        CouponEntity couponEntity = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + couponId));
        return convertToResponseDto(couponEntity);
    }

    @Override
    public List<CouponResponseDto> getAllCoupons() {
        List<CouponEntity> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponseDto updateCoupon(Long couponId, CouponRequestDto couponRequestDto) {
        CouponEntity existingCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + couponId));

        UserEntity user = userRepository.findById(couponRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + couponRequestDto.getUserId()));

        if (!existingCoupon.getCouponCode().equals(couponRequestDto.getCouponCode()) &&
                couponRepository.existsByCouponCode(couponRequestDto.getCouponCode())) {
            throw new RuntimeException("Coupon code already exists: " + couponRequestDto.getCouponCode());
        }

        existingCoupon.setCouponDescription(couponRequestDto.getCouponDescription());
        existingCoupon.setCouponDiscount(couponRequestDto.getCouponDiscount());
        existingCoupon.setValidFromDate(LocalDate.parse(couponRequestDto.getValidFromDate(), DATE_FORMATTER));
        existingCoupon.setValidUntilDate(LocalDate.parse(couponRequestDto.getValidUntilDate(), DATE_FORMATTER));
        existingCoupon.setCouponCode(couponRequestDto.getCouponCode());
        existingCoupon.setUser(user);
        existingCoupon.setCategory(couponRequestDto.getCategory());

        if (couponRequestDto.getStatus() != null) {
            existingCoupon.setStatus(couponRequestDto.getStatus());
        }
        if (couponRequestDto.getIsUsed() != null) {
            existingCoupon.setIsUsed(couponRequestDto.getIsUsed());
        }

        CouponEntity updatedCoupon = couponRepository.save(existingCoupon);
        return convertToResponseDto(updatedCoupon);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new RuntimeException("Coupon not found with id: " + couponId);
        }
        couponRepository.deleteById(couponId);
    }

    @Override
    public CouponResponseDto getCouponByCode(String couponCode) {
        CouponEntity couponEntity = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Coupon not found with code: " + couponCode));
        return convertToResponseDto(couponEntity);
    }

    @Override
    public List<CouponResponseDto> getAvailableCoupons() {
        List<CouponEntity> coupons = couponRepository.findAvailableCoupons();
        return coupons.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponseDto applyCoupon(String couponCode) {
        CouponEntity couponEntity = couponRepository.findValidCouponByCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Valid coupon not found with code: " + couponCode));

        if (LocalDate.now().isAfter(couponEntity.getValidUntilDate())) {
            couponEntity.setStatus("invalid");
            couponRepository.save(couponEntity);
            throw new RuntimeException("Coupon has expired");
        }

        List<CouponEntity> userCoupons = couponRepository.findByUserUserId(couponEntity.getUser().getUserId());
        LocalDate today = LocalDate.now();
        boolean hasOtherValidCoupons = userCoupons.stream()
                .anyMatch(c -> !c.getCouponId().equals(couponEntity.getCouponId()) &&
                        c.getStatus().equals("valid") &&
                        !c.getIsUsed() &&
                        c.getValidFromDate().isEqual(today));

        if (hasOtherValidCoupons) {
            userCoupons.stream()
                    .filter(c -> !c.getCouponId().equals(couponEntity.getCouponId()) &&
                            c.getStatus().equals("valid") &&
                            !c.getIsUsed())
                    .forEach(c -> {
                        c.setStatus("invalid");
                        couponRepository.save(c);
                    });
        }

        couponEntity.setIsUsed(true);
        CouponEntity updatedCoupon = couponRepository.save(couponEntity);
        return convertToResponseDto(updatedCoupon);
    }

    @Override
    public CouponResponseDto validateCoupon(String couponCode) {
        CouponEntity couponEntity = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Coupon not found with code: " + couponCode));

        if (!couponEntity.getStatus().equals("valid")) {
            throw new RuntimeException("Coupon is not valid");
        }

        if (couponEntity.getIsUsed()) {
            throw new RuntimeException("Coupon has already been used");
        }

        if (LocalDate.now().isAfter(couponEntity.getValidUntilDate())) {
            throw new RuntimeException("Coupon has expired");
        }

        if (LocalDate.now().isBefore(couponEntity.getValidFromDate())) {
            throw new RuntimeException("Coupon is not yet active");
        }

        return convertToResponseDto(couponEntity);
    }

    @Override
    public List<CouponResponseDto> bulkCreateCoupons(CouponRequestDto couponRequestDto, List<Long> userIds) {
        List<CouponEntity> createdCoupons = new ArrayList<>();
        List<String> skippedUserIds = new ArrayList<>();

        for (Long userId : userIds) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            String generatedCouponCode = couponRequestDto.getCouponCode() + "-" + userId;

            // Check if coupon code already exists
            if (couponRepository.existsByCouponCode(generatedCouponCode)) {
                skippedUserIds.add(userId.toString());
                continue;
            }

            CouponEntity couponEntity = new CouponEntity();
            couponEntity.setCouponDescription(couponRequestDto.getCouponDescription());
            couponEntity.setCouponDiscount(couponRequestDto.getCouponDiscount());
            couponEntity.setValidFromDate(LocalDate.parse(couponRequestDto.getValidFromDate(), DATE_FORMATTER));
            couponEntity.setValidUntilDate(LocalDate.parse(couponRequestDto.getValidUntilDate(), DATE_FORMATTER));
            couponEntity.setCouponCode(generatedCouponCode);
            couponEntity.setCreatedAt(LocalDateTime.now());
            couponEntity.setStatus("valid");
            couponEntity.setIsUsed(false);
            couponEntity.setUser(user);
            couponEntity.setCategory(couponRequestDto.getCategory());

            createdCoupons.add(couponRepository.save(couponEntity));
        }

        if (!skippedUserIds.isEmpty()) {
            System.out.println("Skipped creating coupons for user IDs " + skippedUserIds + " due to duplicate coupon codes.");
        }

        return createdCoupons.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserCouponsResponseDto getCouponsByUserId(Long userId) {
        List<CouponEntity> coupons = couponRepository.findByUserUserId(userId);
        List<CouponResponseDto> couponDtos = coupons.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        int totalValidCoupons = (int) coupons.stream()
                .filter(c -> c.getStatus().equals("valid") && !c.getIsUsed() &&
                        !LocalDate.now().isAfter(c.getValidUntilDate()) &&
                        !LocalDate.now().isBefore(c.getValidFromDate()))
                .count();

        int totalExpiredCoupons = (int) coupons.stream()
                .filter(c -> c.getStatus().equals("expired") || LocalDate.now().isAfter(c.getValidUntilDate()))
                .count();

        return new UserCouponsResponseDto(userId, couponDtos, totalValidCoupons, totalExpiredCoupons);
    }

    private CouponResponseDto convertToResponseDto(CouponEntity couponEntity) {
        return new CouponResponseDto(
                couponEntity.getCouponId(),
                couponEntity.getCouponDescription(),
                couponEntity.getCouponDiscount(),
                couponEntity.getValidFromDate().format(DATE_FORMATTER),
                couponEntity.getValidUntilDate().format(DATE_FORMATTER),
                couponEntity.getCreatedAt().format(DATE_TIME_FORMATTER),
                couponEntity.getStatus(),
                couponEntity.getCouponCode(),
                couponEntity.getIsUsed(),
                couponEntity.getUser().getUserId(),
                couponEntity.getCategory()
        );
    }
}