package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.Permissions;

@Repository
public interface PermissionsRepository extends MongoRepository<Permissions, String>{
    
}
