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
public class ProductListing {

    private String _id;
    
    private String product_name;

    private Profile owner;
    
    private String description;

    private Long price;

    private Double quantity;

    private String product_image;

    private String transportation_type;
}
