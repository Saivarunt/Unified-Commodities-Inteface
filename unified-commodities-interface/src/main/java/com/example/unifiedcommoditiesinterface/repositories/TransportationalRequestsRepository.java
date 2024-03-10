package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.TransportationalRequests;
import com.example.unifiedcommoditiesinterface.models.User;


@Repository
public interface TransportationalRequestsRepository extends MongoRepository<TransportationalRequests, String>{
    Page<TransportationalRequests> findByUser(User user, Pageable pageable);
}
