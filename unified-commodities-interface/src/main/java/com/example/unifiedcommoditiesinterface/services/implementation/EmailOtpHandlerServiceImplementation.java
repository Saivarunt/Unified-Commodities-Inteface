package com.example.unifiedcommoditiesinterface.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.EmailOtpHandler;
import com.example.unifiedcommoditiesinterface.repositories.EmailOtpHandlerRepository;
import com.example.unifiedcommoditiesinterface.services.EmailOtpHandlerService;

@Service
public class EmailOtpHandlerServiceImplementation implements EmailOtpHandlerService{

    @Autowired
    private EmailOtpHandlerRepository emailOtpHandlerRepository;

    public String getOtp(String email){
        Integer maxValue = 999999;
        Integer minValue = 100000;
        Integer range = maxValue - minValue + 1;
        Integer otp = (int) (Math.random() * range) + minValue;

        emailOtpHandlerRepository.save(new EmailOtpHandler(null, email, otp.toString()));
        return otp.toString();
    }

    public Boolean verifyOtp(String mail_id, String otp){
        List<EmailOtpHandler> emails = emailOtpHandlerRepository.findByMail_id(mail_id);
        EmailOtpHandler finalMail = emails.get(emails.size() - 1);
        return finalMail.getOtp().equals(otp);
    }
}
