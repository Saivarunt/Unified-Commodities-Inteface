package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.unifiedcommoditiesinterface.models.ProductRating;
import com.example.unifiedcommoditiesinterface.models.Products;

import java.util.List;


public interface ProductRatingRepository extends MongoRepository<ProductRating, String>{
    List<ProductRating> findByProduct(Products product);
}
