package com.example.unifiedcommoditiesinterface.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class PurchaseInfo {

    @Id
    private String _id;

    @DocumentReference
    private Products product;

    @DocumentReference
    private User buyer;
    
    private Double quantity;

    private Long price;

    private String transportation_type;
}