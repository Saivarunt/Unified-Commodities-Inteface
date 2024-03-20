package com.example.unifiedcommoditiesinterface.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import com.example.unifiedcommoditiesinterface.models.TransporterProposal;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class TransporterProposalDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TransporterProposal> findTransportersByFullName(String fullName, Integer page) {
        LookupOperation userLookup = Aggregation.lookup("user", "transporter", "_id", "transporter_user_details");

        LookupOperation profileLookup = LookupOperation.newLookup()
                .from("profile")
                .localField("transporter_user_details.profile")
                .foreignField("_id")
                .as("transporter_profile");

        MatchOperation matchProfileByName = Aggregation.match(Criteria.where("transporter_profile.full_name").regex(Pattern.compile(fullName, Pattern.CASE_INSENSITIVE)));

        ProjectionOperation projectFields = Aggregation.project("request", "transporter", "status");

        SkipOperation skipOperation = Aggregation.skip(page * 20);
        
        LimitOperation limitOperation = Aggregation.limit(20);

        Aggregation aggregation = Aggregation.newAggregation(
                userLookup,
                profileLookup,
                matchProfileByName,
                projectFields,
                skipOperation,
                limitOperation
        );

        return mongoTemplate.aggregate(aggregation, "transporterProposal", TransporterProposal.class).getMappedResults();
    }

}
    


