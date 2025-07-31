package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.bcrypt.BcryptEncoderConfig;
import com.oy.oy_jewels.dto.request.OtpVerificationDto;
import com.oy.oy_jewels.entity.AdminEntity;
import com.oy.oy_jewels.entity.OtpEntity;
import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.AdminRepository;
import com.oy.oy_jewels.repository.OtpRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final OtpRepository otpRepository;
    private final BcryptEncoderConfig passwordEncoder;
//    private final SmsService smsService; // Your SMS service implementation

    @Override
    @Transactional
    public void sendOtp(String mobileNumber) {

        // Auto-clean expired OTPs for ALL users (new)
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());

        UserEntity user = userRepository.findUserByMobile(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found with mobile: " + mobileNumber));

        AdminEntity admin = adminRepository.findAdminByMobile(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found with mobile: " + mobileNumber));

        // Invalidate previous OTPs
        otpRepository.deleteByUserId(user.getUserId());

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Create and save OTP
        OtpEntity otpEntity = new OtpEntity(
                user,
                admin,
                passwordEncoder.encode(otp), // Hash the OTP
                mobileNumber,
                LocalDateTime.now().plusMinutes(5) // Expires in 5 minutes
        );
        otpRepository.save(otpEntity);

        // Send OTP via SMS
//        smsService.sendSms(mobileNumber, "Your OTP is: " + otp + ". Valid for 5 minutes.");
    }

    @Override
    @Transactional
    public boolean verifyOtp(OtpVerificationDto otpVerificationDto) {
        UserEntity user = userRepository.findUserByMobile(otpVerificationDto.getMobileNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OtpEntity> validOtps = otpRepository.findValidOtps(
                user.getUserId(),
                otpVerificationDto.getMobileNumber(),
                LocalDateTime.now()
        );

        if (validOtps.isEmpty()) {
            return false;
        }

        // Check if any OTP matches
        boolean otpMatched = validOtps.stream()
                .anyMatch(otp -> passwordEncoder.matches(otpVerificationDto.getOtp(), otp.getOtpCode()));

        if (otpMatched) {
            // Update password
            user.setPassword(passwordEncoder.encode(otpVerificationDto.getNewPassword()));
            userRepository.save(user);

            // Mark OTP as used
            validOtps.forEach(otp -> {
                otp.setUsed(true);
                otpRepository.save(otp);
            });
            return true;
        }

        return false;
    }
}