package com.example.unifiedcommoditiesinterface.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String state;
    private String country;
    private String primary_address;
    private String postal_code;
}
