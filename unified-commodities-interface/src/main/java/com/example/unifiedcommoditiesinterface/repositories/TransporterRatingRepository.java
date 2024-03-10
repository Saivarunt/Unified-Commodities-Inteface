package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.unifiedcommoditiesinterface.models.TransporterRating;
import com.example.unifiedcommoditiesinterface.models.User;

import java.util.List;


public interface TransporterRatingRepository extends MongoRepository<TransporterRating, String>{
    List<TransporterRating> findByTransporter(User transporter);
}
