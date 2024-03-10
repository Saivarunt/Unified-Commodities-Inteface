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
public class ProductLifecycle {
    @Id
    private String _id;

    @DocumentReference
    private Products product;

    @DocumentReference
    private User owner;
    
    @DocumentReference
    private User consumer;

    @DocumentReference
    private User transporter;

    private Double quantity;
    
    private String supplier_status;

    private String consumer_status;

}
