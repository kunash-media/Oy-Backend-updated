package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.OtpVerificationDto;

public interface OtpService {
    void sendOtp(String mobileNumber);
    boolean verifyOtp(OtpVerificationDto otpVerificationDto);

    void sendOtpEmail(String email); // Add email method

//    boolean verifyEmailOtp(OtpVerificationDto otpVerificationDto); // Add email verification

}