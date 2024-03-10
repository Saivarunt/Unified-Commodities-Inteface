package com.example.unifiedcommoditiesinterface.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseRequest {
    private String _id;
    
    private Double quantity;

    private String transportation_type;
}
