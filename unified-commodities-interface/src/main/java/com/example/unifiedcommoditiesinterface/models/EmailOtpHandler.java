package com.example.unifiedcommoditiesinterface.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class EmailOtpHandler {
    @Id
    private String _id;

    private String mail_id;

    private String otp;
}
