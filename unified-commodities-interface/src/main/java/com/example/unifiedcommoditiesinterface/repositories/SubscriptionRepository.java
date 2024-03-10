package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.Subscription;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String>{

}
