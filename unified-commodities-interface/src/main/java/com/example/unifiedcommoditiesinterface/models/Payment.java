package com.example.unifiedcommoditiesinterface.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Payment {

    @Id
    private String _id;

    private String paymentId;

    private String orderId;

    private String signature;
    
    private Double amount;
 
}
