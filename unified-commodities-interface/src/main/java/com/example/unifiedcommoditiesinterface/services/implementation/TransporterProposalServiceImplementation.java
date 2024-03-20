package com.example.unifiedcommoditiesinterface.services.implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.dao.TransporterProposalDao;
import com.example.unifiedcommoditiesinterface.models.ProductLifecycle;
import com.example.unifiedcommoditiesinterface.models.TransportationalRequests;
import com.example.unifiedcommoditiesinterface.models.TransporterProposal;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.TransporterProposalListing;
import com.example.unifiedcommoditiesinterface.repositories.ProductLifecycleRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProfileRepository;
import com.example.unifiedcommoditiesinterface.repositories.TransportationalRequestsRepository;
import com.example.unifiedcommoditiesinterface.repositories.TransporterProposalRepository;
import com.example.unifiedcommoditiesinterface.repositories.UserRepository;
import com.example.unifiedcommoditiesinterface.services.TransporterProposalService;

@Service
public class TransporterProposalServiceImplementation implements TransporterProposalService{

    @Autowired
    TransporterProposalRepository transporterProposalRepository;

    @Autowired
    TransportationalRequestsRepository transportationalRequestsRepository;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductLifecycleRepository productLifecycleRepository;

    @Autowired
    TransporterProposalDao transporterProposalDao;
    
    public TransporterProposalListing proposalListing(String _id, User user) {
        TransportationalRequests transportationalRequest = transportationalRequestsRepository.findById(_id).get();
        
        if(transporterProposalRepository.findByRequest(transportationalRequest).stream().anyMatch(val -> val.getTransporter().equals(user))){
            throw new DuplicateKeyException("Cannot make more than 1 proposal per posting"); 
        }
    
        TransporterProposal transporterProposal = transporterProposalRepository.save(new TransporterProposal(null, transportationalRequest, user, "PENDING"));
        return new TransporterProposalListing(transporterProposal.get_id(),transportationalRequest.get_id(), user.getProfile(), transporterProposal.getStatus());
    }

    public TransporterProposalListing fetchProposal(String _id) {
        TransporterProposal transporterProposal = transporterProposalRepository.findById(_id).get();
        return new TransporterProposalListing(transporterProposal.get_id(),transporterProposal.getRequest().get_id(), transporterProposal.getTransporter().getProfile(), transporterProposal.getStatus());
    }

    public Page<TransporterProposalListing> fetchMadeProposals(User user, Integer page) {
        Page<TransporterProposal> transporterProposal = transporterProposalRepository.findByTransporter(user, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "status")));
        List<TransporterProposalListing> response = transporterProposal.getContent().stream()
        .map(val -> new TransporterProposalListing(
            val.get_id(),
            val.getRequest().get_id(), 
            val.getTransporter().getProfile(),
            val.getStatus()))
        .collect(Collectors.toList());

        return new PageImpl<>(response, PageRequest.of(page, 10), transporterProposal.getTotalElements());
    }

    public Page<TransporterProposalListing> fetchProposalsByRequest(String _id, Integer page) {
        TransportationalRequests transportationalRequest = transportationalRequestsRepository.findById(_id).get();
        Page<TransporterProposal> transporterProposal = transporterProposalRepository.findByRequest(transportationalRequest, PageRequest.of(page, 10));
        List<TransporterProposalListing> response= transporterProposal.getContent().stream()
        .map(val -> new TransporterProposalListing(
            val.get_id(),
            val.getRequest().get_id(), 
            val.getTransporter().getProfile(),
            val.getStatus()))
        .collect(Collectors.toList());
        return new PageImpl<>(response, PageRequest.of(page, 10), transporterProposal.getTotalElements());
    }

    public Boolean approveTransporterProposal(String _id) {
        TransporterProposal transporterProposal = transporterProposalRepository.findById(_id).get();
        TransportationalRequests transportationalRequest = transporterProposal.getRequest();
        transportationalRequest.setStatus("INITIATED");
        transportationalRequestsRepository.save(transportationalRequest);
        
        transporterProposal.setStatus("APPROVED");
        TransporterProposal updatedProposal = transporterProposalRepository.save(transporterProposal);

        mailSenderService.sendEmail(updatedProposal.getTransporter().getProfile().getEmail(), "Transport Proposal Acceptance - UCI", 
        "Your proposal for transportation of product "+ updatedProposal.getRequest().getProduct().getProduct_name() + " was accepted by the user.");

        List<TransporterProposal> allProposals = transporterProposalRepository.findByRequest(updatedProposal.getRequest());

        allProposals.stream().filter(val -> val.getStatus().equals("")).forEach(proposal -> {
            proposal.setStatus("REJECTED");
            transporterProposalRepository.save(proposal);

            mailSenderService.sendEmail(proposal.getTransporter().getProfile().getEmail(), "Transport Proposal Rejection - UCI", 
            "Your proposal for transportation of product "+ updatedProposal.getRequest().getProduct().getProduct_name() + " was rejected by the user.");
        });

        ProductLifecycle productLifecycle = new ProductLifecycle(null, updatedProposal.getRequest().getProduct(), updatedProposal.getRequest().getProduct().getOwner(), updatedProposal.getRequest().getUser(), updatedProposal.getTransporter(), updatedProposal.getRequest().getQuantity(), "", "", new Date());
        productLifecycleRepository.save(productLifecycle);
        return true;
    }

    public Boolean rejectTransporterProposal(String _id) {
        TransporterProposal transporterProposal = transporterProposalRepository.findById(_id).get();
        transporterProposal.setStatus("REJECTED");
        transporterProposalRepository.save(transporterProposal); 

        mailSenderService.sendEmail(transporterProposal.getTransporter().getProfile().getEmail(), "Transport Proposal Rejection - UCI", 
        "Your proposal for transportation of product "+ transporterProposal.getRequest().getProduct().getProduct_name() + " was rejected by the user.");
        return true;
    }

    public Page<TransporterProposal> findAllProposals(Integer page) {
        return transporterProposalRepository.findAll(PageRequest.of(page, 20));
    }

    public Page<TransporterProposal> searchAllProposals(String searchValue, Integer page) {
        List<TransporterProposal> proposals = transporterProposalDao.findTransportersByFullName(searchValue, page);
        return new PageImpl<>(proposals, PageRequest.of(page,20), proposals.size());
    }
}
