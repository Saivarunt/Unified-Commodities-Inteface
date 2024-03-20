package com.example.unifiedcommoditiesinterface.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.TransportationalRequests;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class TransportRequestDao {

    
        @Autowired
        private MongoTemplate mongoTemplate;
    
        public List<TransportationalRequests> findRequestsByFullName(String fullName, Integer page) {
            LookupOperation userLookup = LookupOperation.newLookup()
                    .from("user")
                    .localField("user")
                    .foreignField("_id")
                    .as("requester");
    
            LookupOperation profileLookup = LookupOperation.newLookup()
                    .from("profile")
                    .localField("requester.profile")
                    .foreignField("_id")
                    .as("profile");
    
            MatchOperation matchProfileByName = Aggregation.match(Criteria.where("profile.full_name").regex(Pattern.compile(fullName, Pattern.CASE_INSENSITIVE)));
    
            ProjectionOperation projectFields = Aggregation.project("product", "user", "quantity", "type", "status");

            SkipOperation skipOperation = Aggregation.skip(page * 10);
            
            LimitOperation limitOperation = Aggregation.limit(10);

            Aggregation aggregation = Aggregation.newAggregation(
                    userLookup,
                    profileLookup,
                    matchProfileByName,
                    projectFields,
                    skipOperation,
                    limitOperation
            );
    
            return mongoTemplate.aggregate(aggregation, "transportationalRequests", TransportationalRequests.class).getMappedResults();
    
        }
    
}