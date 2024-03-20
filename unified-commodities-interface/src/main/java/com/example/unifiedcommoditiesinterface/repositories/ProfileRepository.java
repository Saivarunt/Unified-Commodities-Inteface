package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.Profile;
import java.util.List;


@Repository
public interface ProfileRepository extends MongoRepository<Profile, String>{
    @Query("{full_name:/?0/}")
    List<Profile> findByFull_name(String full_name);
}
