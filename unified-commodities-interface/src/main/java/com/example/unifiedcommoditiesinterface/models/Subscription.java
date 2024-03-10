package com.example.unifiedcommoditiesinterface.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    private String _id;

    private String subscription_type;

    private String period;

    private Date start_date;
    
    private Date end_date;

    @DocumentReference
    private Payment payment;
}
