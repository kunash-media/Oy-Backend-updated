package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.OtpRequestDto;
import com.oy.oy_jewels.dto.request.OtpVerificationDto;
import com.oy.oy_jewels.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
//@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequestDto otpRequest) {
        otpService.sendOtp(otpRequest.getMobileNumber());
        return ResponseEntity.ok().body("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationDto otpVerification) {
        boolean isVerified = otpService.verifyOtp(otpVerification);
        if (isVerified) {
            return ResponseEntity.ok().body("Password reset successfully");
        }
        return ResponseEntity.badRequest().body("Invalid OTP or expired");
    }
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmailOtp(@RequestParam String email) {
        try {
            otpService.sendOtpEmail(email);
            return ResponseEntity.ok("OTP sent successfully to email: " + email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }

//    @PostMapping("/verify-email")
//    public ResponseEntity<String> verifyEmailOtp(@RequestBody OtpVerificationDto otpVerificationDto) {
//        try {
//            boolean isValid = otpService.verifyEmailOtp(otpVerificationDto);
//            if (isValid) {
//                return ResponseEntity.ok("OTP verified successfully");
//            } else {
//                return ResponseEntity.badRequest().body("Invalid or expired OTP");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Verification failed: " + e.getMessage());
//        }
//    }

    // Simple DTO for email request
    public static class EmailRequest {
        private String email;

        public EmailRequest() {}

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

}