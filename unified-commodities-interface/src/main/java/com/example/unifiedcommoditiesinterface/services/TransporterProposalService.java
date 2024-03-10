package com.example.unifiedcommoditiesinterface.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.TransporterProposal;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.TransporterProposalListing;

@Service
public interface TransporterProposalService {
    public TransporterProposalListing proposalListing(String _id, User user);
    public TransporterProposalListing fetchProposal(String _id);
    public Page<TransporterProposalListing> fetchMadeProposals(User user, Integer page);
    public Page<TransporterProposalListing> fetchProposalsByRequest(String _id, Integer page);
    public Boolean approveTransporterProposal(String _id);
    public Boolean rejectTransporterProposal(String _id);
    public Page<TransporterProposal> findAllProposals(Integer page);
}
