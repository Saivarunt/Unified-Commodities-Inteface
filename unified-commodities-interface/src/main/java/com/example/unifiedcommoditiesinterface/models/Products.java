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
public class Products {
    @Id
    private String _id;

    private String product_name;

    @DocumentReference
    private User owner;

    private String description;

    private Long price;

    private Double quantity;

    private String product_image;

    private String transportation_type;
}
