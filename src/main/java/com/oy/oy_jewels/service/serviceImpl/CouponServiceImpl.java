package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.CouponRequestDto;
import com.oy.oy_jewels.dto.response.CouponResponseDto;
import com.oy.oy_jewels.dto.response.UserCouponsResponseDto;
import com.oy.oy_jewels.entity.CouponEntity;
import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.CouponRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.CouponService;
import com.oy.oy_jewels.service.WhatsAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WhatsAppService whatsAppService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter WHATSAPP_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public CouponResponseDto createCoupon(CouponRequestDto couponRequestDto) {
        logger.debug("Creating coupon with request: ID={}, Code={}", couponRequestDto.getUserId(), couponRequestDto.getCouponCode());

        UserEntity user = userRepository.findById(couponRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + couponRequestDto.getUserId()));

        if (couponRepository.existsByCouponCode(couponRequestDto.getCouponCode())) {
            throw new RuntimeException("Coupon code already exists: " + couponRequestDto.getCouponCode());
        }

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setEventType(couponRequestDto.getEventType());
        couponEntity.setCouponDescription(couponRequestDto.getCouponDescription());
        couponEntity.setCouponDiscount(couponRequestDto.getCouponDiscount());
        try {
            couponEntity.setValidFromDate(LocalDate.parse(couponRequestDto.getValidFromDate(), DATE_FORMATTER));
            couponEntity.setValidUntilDate(LocalDate.parse(couponRequestDto.getValidUntilDate(), DATE_FORMATTER));
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format for validFromDate or validUntilDate, expected yyyy-MM-dd", e);
        }
        couponEntity.setCouponCode(couponRequestDto.getCouponCode());
        couponEntity.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        couponEntity.setStatus(couponRequestDto.getStatus() != null ? couponRequestDto.getStatus() : "valid");
        couponEntity.setIsUsed(couponRequestDto.getIsUsed() != null ? couponRequestDto.getIsUsed() : false);
        couponEntity.setUser(user);
        couponEntity.setCategory(couponRequestDto.getCategory() != null && !couponRequestDto.getCategory().isEmpty()
                ? couponRequestDto.getCategory() : List.of("Jewelry"));

        CouponEntity savedCoupon = couponRepository.save(couponEntity);
        logger.info("Coupon created successfully: ID={}, Code={}", savedCoupon.getCouponId(), savedCoupon.getCouponCode());

        // Send WhatsApp message based on event type
        try {
            String phoneNumber = user.getMobile();
            if (phoneNumber == null || phoneNumber.isBlank()) {
                logger.warn("Skipping WhatsApp message for coupon {}: User {} has no valid phone number",
                        savedCoupon.getCouponId(), user.getUserId());
            } else {
                String userFirstName = user.getCustomerFirstName();
                if (userFirstName == null || userFirstName.isBlank()) {
                    logger.warn("Skipping WhatsApp message for coupon {}: User {} has no valid first name",
                            savedCoupon.getCouponId(), user.getUserId());
                    return convertToResponseDto(savedCoupon);
                }
                String couponDiscount = couponEntity.getCouponDiscount();
                List<String> categories = couponEntity.getCategory();
                String couponCode = couponEntity.getCouponCode();
                String validUntilDate = couponEntity.getValidUntilDate().format(WHATSAPP_DATE_FORMATTER);
                String eventType = couponEntity.getEventType();

                if (isBirthdayCoupon(couponEntity)) {
                    whatsAppService.sendBirthdayCouponMessage(phoneNumber, userFirstName, couponDiscount, categories, couponCode, validUntilDate);
                    logger.info("Sent birthday coupon message for coupon {} to user {}", savedCoupon.getCouponId(), user.getUserId());
                } else if (isAnniversaryCoupon(couponEntity)) {
                    whatsAppService.sendAnniversaryCouponMessage(phoneNumber, userFirstName, couponDiscount, categories, couponCode, validUntilDate);
                    logger.info("Sent anniversary coupon message for coupon {} to user {}", savedCoupon.getCouponId(), user.getUserId());
                } else if (isFestivalCoupon(couponEntity)) {
                    String festivalName = extractFestivalName(eventType);
                    whatsAppService.sendFestivalCouponMessage(phoneNumber, userFirstName, couponDiscount, categories, couponCode, validUntilDate, festivalName);
                    logger.info("Sent festival coupon message for coupon {} to user {}", savedCoupon.getCouponId(), user.getUserId());
                } else {
                    logger.debug("Coupon {} is not a special coupon, skipping WhatsApp message", savedCoupon.getCouponId());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send WhatsApp message for coupon {}: {}", savedCoupon.getCouponId(), e.getMessage(), e);
            throw new RuntimeException("Failed to send WhatsApp message for coupon " + savedCoupon.getCouponId(), e);
        }

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

        existingCoupon.setEventType(couponRequestDto.getEventType());
        existingCoupon.setCouponDescription(couponRequestDto.getCouponDescription());
        existingCoupon.setCouponDiscount(couponRequestDto.getCouponDiscount());
        try {
            existingCoupon.setValidFromDate(LocalDate.parse(couponRequestDto.getValidFromDate(), DATE_FORMATTER));
            existingCoupon.setValidUntilDate(LocalDate.parse(couponRequestDto.getValidUntilDate(), DATE_FORMATTER));
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format for validFromDate or validUntilDate, expected yyyy-MM-dd", e);
        }
        existingCoupon.setCouponCode(couponRequestDto.getCouponCode());
        existingCoupon.setUser(user);
        existingCoupon.setCategory(couponRequestDto.getCategory() != null && !couponRequestDto.getCategory().isEmpty()
                ? couponRequestDto.getCategory() : List.of("Jewelry"));

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
        logger.debug("Bulk creating coupons with request: ID={}, userIds: {}", couponRequestDto.getUserId(), userIds);
        List<CouponEntity> createdCoupons = new ArrayList<>();
        List<String> skippedUserIds = new ArrayList<>();

        for (Long userId : userIds) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            String generatedCouponCode = couponRequestDto.getCouponCode() + "-" + userId;

            if (couponRepository.existsByCouponCode(generatedCouponCode)) {
                skippedUserIds.add(userId.toString());
                continue;
            }

            CouponEntity couponEntity = new CouponEntity();
            couponEntity.setEventType(couponRequestDto.getEventType());
            couponEntity.setCouponDescription(couponRequestDto.getCouponDescription());
            couponEntity.setCouponDiscount(couponRequestDto.getCouponDiscount());
            try {
                couponEntity.setValidFromDate(LocalDate.parse(couponRequestDto.getValidFromDate(), DATE_FORMATTER));
                couponEntity.setValidUntilDate(LocalDate.parse(couponRequestDto.getValidUntilDate(), DATE_FORMATTER));
            } catch (Exception e) {
                throw new RuntimeException("Invalid date format for validFromDate or validUntilDate, expected yyyy-MM-dd", e);
            }
            couponEntity.setCouponCode(generatedCouponCode);
            couponEntity.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
            couponEntity.setStatus("valid");
            couponEntity.setIsUsed(false);
            couponEntity.setUser(user);
            couponEntity.setCategory(couponRequestDto.getCategory() != null && !couponRequestDto.getCategory().isEmpty()
                    ? couponRequestDto.getCategory() : List.of("Jewelry"));

            CouponEntity savedCoupon = couponRepository.save(couponEntity);
            createdCoupons.add(savedCoupon);
            logger.info("Coupon created in bulk: ID={}, Code={}", savedCoupon.getCouponId(), savedCoupon.getCouponCode());

            // Send WhatsApp message based on event type
            try {
                String phoneNumber = user.getMobile();
                if (phoneNumber == null || phoneNumber.isBlank()) {
                    logger.warn("Skipping WhatsApp message for coupon {}: User {} has no valid phone number",
                            savedCoupon.getCouponId(), user.getUserId());
                } else {
                    String userFirstName = user.getCustomerFirstName();
                    if (userFirstName == null || userFirstName.isBlank()) {
                        logger.warn("Skipping WhatsApp message for coupon {}: User {} has no valid first name",
                                savedCoupon.getCouponId(), user.getUserId());
                        continue;
                    }
                    String couponDiscount = couponEntity.getCouponDiscount();
                    List<String> categories = couponEntity.getCategory();
                    String couponCode = generatedCouponCode;
                    String validUntilDate = couponEntity.getValidUntilDate().format(WHATSAPP_DATE_FORMATTER);
                    String eventType = couponEntity.getEventType();

                    if (isBirthdayCoupon(couponEntity)) {
                        whatsAppService.sendBirthdayCouponMessage(phoneNumber, userFirstName, couponDiscount, categories, couponCode, validUntilDate);
                        logger.info("Sent birthday coupon message for coupon {} to user {}", savedCoupon.getCouponId(), user.getUserId());
                    } else if (isAnniversaryCoupon(couponEntity)) {
                        whatsAppService.sendAnniversaryCouponMessage(phoneNumber, userFirstName, couponDiscount, categories, couponCode, validUntilDate);
                        logger.info("Sent anniversary coupon message for coupon {} to user {}", savedCoupon.getCouponId(), user.getUserId());
                    } else if (isFestivalCoupon(couponEntity)) {
                        String festivalName = extractFestivalName(eventType);
                        whatsAppService.sendFestivalCouponMessage(phoneNumber, userFirstName, couponDiscount, categories, couponCode, validUntilDate, festivalName);
                        logger.info("Sent festival coupon message for coupon {} to user {}", savedCoupon.getCouponId(), user.getUserId());
                    } else {
                        logger.debug("Coupon {} is not a special coupon, skipping WhatsApp message", savedCoupon.getCouponId());
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to send WhatsApp message for coupon {}: {}", savedCoupon.getCouponId(), e.getMessage(), e);
                throw new RuntimeException("Failed to send WhatsApp message for coupon " + savedCoupon.getCouponId(), e);
            }
        }

        if (!skippedUserIds.isEmpty()) {
            logger.warn("Skipped creating coupons for user IDs {} due to duplicate coupon codes.", skippedUserIds);
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
                couponEntity.getEventType(),
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


    //------------  eventType based helper method  for : birthday, anniversary and festival -----------//

    private boolean isBirthdayCoupon(CouponEntity couponEntity) {
        String eventType = couponEntity.getEventType();
        boolean isBirthday = eventType != null && eventType.toLowerCase().contains("birthday");
        logger.debug("Checking if coupon {} is a birthday coupon: eventType='{}', isBirthday={}",
                couponEntity.getCouponId(), eventType, isBirthday);
        return isBirthday;
    }

    private boolean isAnniversaryCoupon(CouponEntity couponEntity) {
        String eventType = couponEntity.getEventType();
        boolean isAnniversary = eventType != null && eventType.toLowerCase().contains("anniversary");
        logger.debug("Checking if coupon {} is an anniversary coupon: eventType='{}', isAnniversary={}",
                couponEntity.getCouponId(), eventType, isAnniversary);
        return isAnniversary;
    }

    private boolean isFestivalCoupon(CouponEntity couponEntity) {
        String eventType = couponEntity.getEventType();
        boolean isFestival = eventType != null && eventType.toLowerCase().contains("festival");
        logger.debug("Checking if coupon {} is a festival coupon: eventType='{}', isFestival={}",
                couponEntity.getCouponId(), eventType, isFestival);
        return isFestival;
    }


    private String extractFestivalName(String eventType) {
        if (eventType == null || eventType.trim().isEmpty()) {
            logger.warn("Event type is null or empty, defaulting to 'Festival'");
            return "Festival";
        }

        String lowerEventType = eventType.toLowerCase();
        if (lowerEventType.contains("festival")) {
            String[] words = eventType.split("\\s+");
            StringBuilder festivalName = new StringBuilder();
            boolean festivalFound = false;
            for (int i = 0; i < words.length; i++) {
                if (words[i].toLowerCase().contains("festival")) {
                    festivalFound = true;
                    continue;
                }
                if (festivalFound && i > 0 && !words[i - 1].toLowerCase().equals("offer")) {
                    festivalName.append(capitalize(words[i])).append(" ");
                }
            }
            if (festivalName.length() > 0) {
                return festivalName.toString().trim();
            }
        }

        // Enhanced fallback with configurable list
        if (lowerEventType.contains("diwali")) return "Diwali";
        if (lowerEventType.contains("christmas")) return "Christmas";
        if (lowerEventType.contains("holi")) return "Holi";
        if (lowerEventType.contains("eid")) return "Eid";
        if (lowerEventType.contains("navratri")) return "Navratri";
        if (lowerEventType.contains("rakhi")) return "Rakhi";
        if (lowerEventType.contains("independence")) return "Independence Day";

        logger.warn("No specific festival name found in eventType '{}', defaulting to 'Festival'", eventType);
        return "Festival";
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}