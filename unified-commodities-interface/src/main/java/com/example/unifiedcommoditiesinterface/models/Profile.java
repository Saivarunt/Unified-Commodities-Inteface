package com.example.unifiedcommoditiesinterface.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.example.unifiedcommoditiesinterface.models.dto.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    private String _id;

    private String full_name;

    private String organization;

    private String email;

    private String phone_number;

    private Address address;

    private Integer rating;

    private Integer delivery_count;
    
    private String profile_image;
    
    @DocumentReference
    private Subscription subscription;
}
