package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.unifiedcommoditiesinterface.models.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String>{
    
}
