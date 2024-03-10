package com.example.unifiedcommoditiesinterface.models.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileInformation {
    private String full_name;

    private String organization;

    private String phone_number;

    private Address address;

    private MultipartFile profile_image;

    private String subscription_type;

    private String period;

    private String razorpay_payment_id;

    private String razorpay_order_id;

    private String razorpay_signature;

    private String amount;

}
