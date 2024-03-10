package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.TransportationalRequests;
import com.example.unifiedcommoditiesinterface.models.TransporterProposal;
import com.example.unifiedcommoditiesinterface.models.User;
import java.util.List;



@Repository
public interface TransporterProposalRepository extends MongoRepository<TransporterProposal, String>{
    List<TransporterProposal> findByRequest(TransportationalRequests request);
    Page<TransporterProposal> findByTransporter(User transporter, Pageable pageable);
    Page<TransporterProposal> findByRequest(TransportationalRequests request, Pageable pageable);
}
