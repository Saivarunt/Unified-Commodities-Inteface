package com.example.unifiedcommoditiesinterface.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LifecycleView {

    private String _id;
    
    private String product;

    private String supplier;
    
    private String consumer;

    private String transporter;

    private Double quantity;
    
    private String supplier_status;

    private String consumer_status;

}
