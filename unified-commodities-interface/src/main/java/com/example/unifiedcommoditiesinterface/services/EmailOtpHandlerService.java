package com.example.unifiedcommoditiesinterface.services;

import org.springframework.stereotype.Service;

@Service
public interface EmailOtpHandlerService {
    public String getOtp(String email);
    public Boolean verifyOtp(String mail_id, String otp);
}
