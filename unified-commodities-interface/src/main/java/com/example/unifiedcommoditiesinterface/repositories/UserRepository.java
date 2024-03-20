package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.User;
import java.util.Optional;




@Repository
public interface UserRepository  extends MongoRepository<User, String>{
    @Query("{username:/?0/}")
    Page<User> findByUsername(String username, Pageable pageable);
    Optional<User> findByUsername(String username);
    Optional<User> findByProfile(Profile profile);
}
