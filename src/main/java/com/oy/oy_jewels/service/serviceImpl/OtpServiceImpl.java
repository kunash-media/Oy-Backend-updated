package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.bcrypt.BcryptEncoderConfig;
import com.oy.oy_jewels.dto.request.OtpVerificationDto;
import com.oy.oy_jewels.entity.AdminEntity;
import com.oy.oy_jewels.entity.OtpEntity;
import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.AdminRepository;
import com.oy.oy_jewels.repository.OtpRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.EmailService;
import com.oy.oy_jewels.service.OtpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final OtpRepository otpRepository;
    private final BcryptEncoderConfig passwordEncoder;
    private final EmailService emailService;

    public OtpServiceImpl(UserRepository userRepository, AdminRepository adminRepository,
                          OtpRepository otpRepository, BcryptEncoderConfig passwordEncoder,
                          EmailService emailService) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }
    //    private final SmsService smsService; // Your SMS service implementation
    @Override
    @Transactional
    public void sendOtp(String mobileNumber) {
        // Auto-clean expired OTPs for ALL users
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());

        // Try to find user first, then admin
        UserEntity user = userRepository.findUserByMobile(mobileNumber).orElse(null);
        AdminEntity admin = null;

        if (user == null) {
            admin = adminRepository.findAdminByMobile(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found with mobile: " + mobileNumber));
        }

        // Invalidate previous OTPs
        if (user != null) {
            otpRepository.deleteByUserId(user.getUserId());
        }

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Create and save OTP using the full constructor
        OtpEntity otpEntity = new OtpEntity(
                null, // id - let JPA auto-generate
                user,
                admin,
                passwordEncoder.encode(otp), // Hash the OTP
                mobileNumber,
                LocalDateTime.now(), // createdAt
                LocalDateTime.now().plusMinutes(5), // expiresAt
                false, // isUsed
                null // email (null for SMS OTP)
        );
        otpRepository.save(otpEntity);

        // Send OTP via SMS
        // smsService.sendSms(mobileNumber, "Your OTP is: " + otp + ". Valid for 5 minutes.");
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


    @Override
    @Transactional
    public void sendOtpEmail(String email) {
        // Auto-clean expired OTPs
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());

        // Try to find user first, then admin
        UserEntity user = userRepository.findUserByEmail(email).orElse(null);
        AdminEntity admin = null;

        if (user == null) {
            admin = adminRepository.findAdminByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        }

        // Invalidate previous OTPs for this user
        if (user != null) {
            otpRepository.deleteByUserId(user.getUserId());
        }

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Create and save OTP using the full constructor
        OtpEntity otpEntity = new OtpEntity(
                null, // id - let JPA auto-generate
                user,
                admin,
                passwordEncoder.encode(otp), // Hash the OTP
                user != null ? user.getMobile() : null, // Keep mobile for reference
                LocalDateTime.now(), // createdAt
                LocalDateTime.now().plusMinutes(5), // expiresAt
                false, // isUsed
                email // email
        );
        otpRepository.save(otpEntity);

        // Send OTP via Email
        emailService.sendOtpEmail(email, otp);
    }



//    @Override
//    @Transactional
//    public boolean verifyEmailOtp(OtpVerificationDto otpVerificationDto) {
//        UserEntity user = userRepository.findUserByEmail(otpVerificationDto.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // You'll need to create this method in repository
//        List<OtpEntity> validOtps = otpRepository.findValidOtpsByEmail(
//                user.getUserId(),
//                otpVerificationDto.getEmail(),
//                LocalDateTime.now()
//        );
//
//        if (validOtps.isEmpty()) {
//            return false;
//        }
//
//        // Check if any OTP matches
//        boolean otpMatched = validOtps.stream()
//                .anyMatch(otp -> passwordEncoder.matches(otpVerificationDto.getOtp(), otp.getOtpCode()));
//
//        if (otpMatched) {
//            // Update password if needed
//            if (otpVerificationDto.getNewPassword() != null) {
//                user.setPassword(passwordEncoder.encode(otpVerificationDto.getNewPassword()));
//                userRepository.save(user);
//            }
//
//            // Mark OTP as used
//            validOtps.forEach(otp -> {
//                otp.setUsed(true);
//                otpRepository.save(otp);
//            });
//            return true;
//        }
//
//        return false;
//    }

}