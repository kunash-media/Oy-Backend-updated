package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.OtpRequestDto;
import com.oy.oy_jewels.dto.request.OtpVerificationDto;
import com.oy.oy_jewels.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
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
}