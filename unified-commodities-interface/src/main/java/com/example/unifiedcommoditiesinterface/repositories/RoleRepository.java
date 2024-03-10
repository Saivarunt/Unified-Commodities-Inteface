package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends MongoRepository<Role,String>{

    Optional<Role> findByAuthority(String authority);
    
}