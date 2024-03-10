package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.Profile;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String>{
    
}
