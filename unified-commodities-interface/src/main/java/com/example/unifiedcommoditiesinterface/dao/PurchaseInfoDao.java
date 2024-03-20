package com.example.unifiedcommoditiesinterface.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.PurchaseInfo;

import java.util.List;
import java.util.regex.Pattern;
@Service
public class PurchaseInfoDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<PurchaseInfo> findOrdersByProductName(String productName, Integer page) {
        LookupOperation productLookup = LookupOperation.newLookup()
                .from("products")
                .localField("product")
                .foreignField("_id")
                .as("product_info");

        MatchOperation matchProductByName = Aggregation.match(Criteria.where("product_info.product_name").regex(Pattern.compile(productName, Pattern.CASE_INSENSITIVE)));

        ProjectionOperation projectFields = Aggregation.project("product", "buyer", "quantity", "price", "transportation_type");

        SkipOperation skipOperation = Aggregation.skip(page * 20);
            
        LimitOperation limitOperation = Aggregation.limit(20);
        
        Aggregation aggregation = Aggregation.newAggregation(
                productLookup,
                matchProductByName,
                projectFields,
                skipOperation,
                limitOperation
        );

        return mongoTemplate.aggregate(aggregation, "purchaseInfo", PurchaseInfo.class).getMappedResults();
    }
}
