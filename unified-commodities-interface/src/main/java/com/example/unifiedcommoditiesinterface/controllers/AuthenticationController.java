package com.example.unifiedcommoditiesinterface.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.LoginResponse;
import com.example.unifiedcommoditiesinterface.models.dto.MailResponse;
import com.example.unifiedcommoditiesinterface.models.dto.Registration;
import com.example.unifiedcommoditiesinterface.models.dto.RegistrationResponse;
import com.example.unifiedcommoditiesinterface.services.EmailOtpHandlerService;
import com.example.unifiedcommoditiesinterface.services.SubscriptionService;
import com.example.unifiedcommoditiesinterface.services.UserService;
import com.example.unifiedcommoditiesinterface.services.implementation.AuthenticationService;
import com.example.unifiedcommoditiesinterface.services.implementation.MailSenderService;
import com.example.unifiedcommoditiesinterface.services.implementation.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    EmailOtpHandlerService emailOtpHandlerService;

    @Autowired
    SubscriptionService subscriptionService;

    
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;
    
    @PostMapping("/supplier-registration")
    public ResponseEntity<RegistrationResponse> registUserAsSupplier(@RequestBody Registration body) {
        return new ResponseEntity<RegistrationResponse>(authenticationService.registerUser(body,"SUPPLIER"), HttpStatus.OK);
    }

    @PostMapping("/consumer-registration")
    public ResponseEntity<RegistrationResponse> registUserAsConsumer(@RequestBody Registration body) {
        return new ResponseEntity<RegistrationResponse>(authenticationService.registerUser(body,"CONSUMER"), HttpStatus.OK);

    }

    @PostMapping("/transporter-registration")
    public ResponseEntity<RegistrationResponse> registUserAsTransporter(@RequestBody Registration body) {
        return new ResponseEntity<RegistrationResponse>(authenticationService.registerUser(body,"TRANSPORTER"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody Registration body) {
        return new ResponseEntity<LoginResponse>(authenticationService.loginUser(body), HttpStatus.OK);
    }

    @PostMapping("/generate-otp/{mail_id}")
    public ResponseEntity<MailResponse> generateOtp(@PathVariable String mail_id){
        String otp = emailOtpHandlerService.getOtp(mail_id);
        return new ResponseEntity<>(new MailResponse(mailSenderService.sendEmail(mail_id, "OTP to verify email", "YOUR OTP: " + otp)), HttpStatus.OK);
    }

    @PostMapping("/verify-otp/")
    public ResponseEntity<Boolean> verifyOtp(@PathParam(value = "mail_id") String mail_id, @PathParam(value = "otp") String otp) {
        return new ResponseEntity<>(emailOtpHandlerService.verifyOtp(mail_id, otp), HttpStatus.OK);
    }
    
    @GetMapping("/validate-subscription")
    public ResponseEntity<Boolean> validateSubscription(HttpServletRequest request) {
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(subscriptionService.isValid(user), HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 * * * *") // hourly scheduler
    // @Scheduled(cron = "*/10 * * * * *") // every 10 seconds
    public void subscriptionValidator() {
        List<User> users = userService.fetchAllUsers();
        users.stream().forEach(val -> {
            System.out.println(val.getUsername()+" "+subscriptionService.isValid(val));
        });
    }

}
