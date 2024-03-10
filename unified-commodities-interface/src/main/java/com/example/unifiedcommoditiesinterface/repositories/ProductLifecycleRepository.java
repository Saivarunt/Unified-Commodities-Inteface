package com.example.unifiedcommoditiesinterface.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.ProductLifecycle;
import com.example.unifiedcommoditiesinterface.models.Products;
import com.example.unifiedcommoditiesinterface.models.User;
import java.util.List;



@Repository
public interface ProductLifecycleRepository extends MongoRepository<ProductLifecycle, String>{
    List<ProductLifecycle> findByProduct(Products product);
    Page<ProductLifecycle> findByProduct(Products product,Pageable pageable);
    Page<ProductLifecycle> findByConsumer(User consumer, Pageable pageable);
    Page<ProductLifecycle> findByTransporter(User transporter, Pageable pageable);
    Page<ProductLifecycle> findByOwner(User owner, Pageable pageable);
}
