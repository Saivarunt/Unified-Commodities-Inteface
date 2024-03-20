package com.example.unifiedcommoditiesinterface.models.dto;

import com.example.unifiedcommoditiesinterface.models.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListTransportationalRequest {
    private String _id;
    
    private ProductListing product;
    
    private Profile user;

    private Double quantity;
    
    private String status;

    private String posted_at;
}