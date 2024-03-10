package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.PurchaseInfo;

@Repository
public interface PurchaseInfoRepository extends MongoRepository<PurchaseInfo, String>{
    
}
