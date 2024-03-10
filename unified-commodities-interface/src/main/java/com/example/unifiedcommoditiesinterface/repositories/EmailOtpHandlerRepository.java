package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.EmailOtpHandler;
import java.util.List;

@Repository
public interface EmailOtpHandlerRepository extends MongoRepository<EmailOtpHandler, String>{
    @Query("{mail_id:?0}")
    List<EmailOtpHandler> findByMail_id(String mail_id);
}
