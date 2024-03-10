package com.example.unifiedcommoditiesinterface.models;

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
public class TransportationalRequests {
    @Id
    private String _id;

    @DocumentReference
    private Products product;

    @DocumentReference
    private User user;
    
    private Double quantity;
    
    private String type;

    private String status;
}
