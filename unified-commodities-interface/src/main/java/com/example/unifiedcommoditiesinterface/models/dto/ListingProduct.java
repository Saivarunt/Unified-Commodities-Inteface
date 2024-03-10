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
public class ListingProduct {
    private String id;
    
    private String product_name;
    
    private String description;

    private String price;

    private String quantity;

    private MultipartFile product_image;

    private String transportation_type;
}
