package com.example.unifiedcommoditiesinterface.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service 
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;
 
    public String sendEmail(String toEmail,String subject,String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("saiv2730@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        return "Mail Sent successfully";
    }
}
