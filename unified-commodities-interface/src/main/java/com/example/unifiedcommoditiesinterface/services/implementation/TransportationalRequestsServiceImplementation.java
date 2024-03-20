package com.example.unifiedcommoditiesinterface.services.implementation;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.dao.TransportRequestDao;
import com.example.unifiedcommoditiesinterface.models.Products;
import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.TransportationalRequests;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.ListTransportationalRequest;
import com.example.unifiedcommoditiesinterface.models.dto.ProductListing;
import com.example.unifiedcommoditiesinterface.repositories.TransportationalRequestsRepository;
import com.example.unifiedcommoditiesinterface.services.TransportationalRequestsService;

@Service
public class TransportationalRequestsServiceImplementation implements TransportationalRequestsService {

    @Autowired
    TransportationalRequestsRepository transportationalRequestsRepository;

    @Autowired
    TransportRequestDao transportRequestDao;

    public String makeRequest(Products product, User user ,String transportational_type, Double quantity) {
        if(transportational_type.equals("Self")){
            return "INITIATED";
        }
        else{
            TransportationalRequests transportationalRequest = new TransportationalRequests(null, product, user, quantity,transportational_type, "AWAITING-TRANSPORTATION", new Date());
            transportationalRequestsRepository.save(transportationalRequest);
            return "AWAITING-TRANSPORTATION";
        }
    }

    public Page<ListTransportationalRequest> fetchTransportationalRequests(Integer page) {
        Page<TransportationalRequests> transportationalRequests = transportationalRequestsRepository.findAll(PageRequest.of(page, 10));

        List<ListTransportationalRequest> response = transportationalRequests.getContent().stream()
        .map(val -> new ListTransportationalRequest(
                        val.get_id(),
                        new ProductListing(
                            val.getProduct().get_id(),
                            val.getProduct().getProduct_name(),
                            val.getProduct().getOwner().getProfile(),
                            val.getProduct().getDescription(),
                            val.getProduct().getPrice(),
                            val.getProduct().getQuantity(),
                            val.getProduct().getProduct_image(),
                            val.getProduct().getTransportation_type()
                        ),
                        new Profile(
                            val.getUser().getProfile().get_id(),
                            val.getUser().getProfile().getFull_name(),
                            val.getUser().getProfile().getOrganization(),
                            val.getUser().getProfile().getEmail(),
                            val.getUser().getProfile().getPhone_number(),
                            val.getUser().getProfile().getAddress(),
                            val.getUser().getProfile().getRating(),
                            val.getUser().getProfile().getDelivery_count(),
                            val.getUser().getProfile().getProfile_image(),
                            val.getUser().getProfile().getSubscription()

                        ),
                        val.getQuantity(),
                        val.getStatus(),
                        dateToDisplay(val.getDate_posted())
                        ))
        .collect(Collectors.toList());
        
        return new PageImpl<>(response, PageRequest.of(page, 10), transportationalRequests.getTotalElements());
    } 
    
    public String dateToDisplay (Date datePosted) {
        StringBuilder todisplay = new StringBuilder();
        
        if(getDateDiff(new Date(), datePosted, TimeUnit.DAYS) > 0) {
            todisplay.append(Long.toString(getDateDiff(new Date(), datePosted, TimeUnit.DAYS)) + " Day(s) "); 
        }
        
        if(getDateDiff(new Date(), datePosted, TimeUnit.HOURS) > 0 && getDateDiff(new Date(), datePosted, TimeUnit.HOURS) < 24) {
            todisplay.append(Long.toString(getDateDiff(new Date(), datePosted, TimeUnit.HOURS)) + " Hour(s) ");
        }

        if(getDateDiff(new Date(), datePosted, TimeUnit.MINUTES) >= 0 && getDateDiff(new Date(), datePosted, TimeUnit.MINUTES) < 60) {
            todisplay.append(Long.toString(getDateDiff(new Date(), datePosted, TimeUnit.MINUTES)) + " Minute(s) ");
        }

        return todisplay.toString();
    }

    public static long getDateDiff(final Date date1, final Date date2, final TimeUnit timeUnit) {

        long diffInMillies = date1.getTime() - date2.getTime();

        return timeUnit.convert(diffInMillies, timeUnit.MILLISECONDS);
    }

    public Page<ListTransportationalRequest> fetchLatestTransportationalRequests(Integer page) {
        Page<TransportationalRequests> transportationalRequests = transportationalRequestsRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "date_posted")));

        List<ListTransportationalRequest> response = transportationalRequests.getContent().stream()
        .map(val -> new ListTransportationalRequest(
                        val.get_id(),
                        new ProductListing(
                            val.getProduct().get_id(),
                            val.getProduct().getProduct_name(),
                            val.getProduct().getOwner().getProfile(),
                            val.getProduct().getDescription(),
                            val.getProduct().getPrice(),
                            val.getProduct().getQuantity(),
                            val.getProduct().getProduct_image(),
                            val.getProduct().getTransportation_type()
                        ),
                        new Profile(
                            val.getUser().getProfile().get_id(),
                            val.getUser().getProfile().getFull_name(),
                            val.getUser().getProfile().getOrganization(),
                            val.getUser().getProfile().getEmail(),
                            val.getUser().getProfile().getPhone_number(),
                            val.getUser().getProfile().getAddress(),
                            val.getUser().getProfile().getRating(),
                            val.getUser().getProfile().getDelivery_count(),
                            val.getUser().getProfile().getProfile_image(),
                            val.getUser().getProfile().getSubscription()

                        ),
                        val.getQuantity(),
                        val.getStatus(),
                        dateToDisplay(val.getDate_posted())
                        ))
        .collect(Collectors.toList());
        
        return new PageImpl<>(response, PageRequest.of(page, 10), transportationalRequests.getTotalElements());
    }


    public ListTransportationalRequest fetchTransportationalRequestsById(String _id) {
        TransportationalRequests transportationalRequests = transportationalRequestsRepository.findById(_id).get();
        
        return new ListTransportationalRequest(
            transportationalRequests.get_id(),
            new ProductListing(
                transportationalRequests.getProduct().get_id(),
                transportationalRequests.getProduct().getProduct_name(),
                transportationalRequests.getProduct().getOwner().getProfile(),
                transportationalRequests.getProduct().getDescription(),
                transportationalRequests.getProduct().getPrice(),
                transportationalRequests.getProduct().getQuantity(),
                transportationalRequests.getProduct().getProduct_image(),
                transportationalRequests.getProduct().getTransportation_type()
            ),
            new Profile(
                transportationalRequests.getUser().getProfile().get_id(),
                transportationalRequests.getUser().getProfile().getFull_name(),
                transportationalRequests.getUser().getProfile().getOrganization(),
                transportationalRequests.getUser().getProfile().getEmail(),
                transportationalRequests.getUser().getProfile().getPhone_number(),
                transportationalRequests.getUser().getProfile().getAddress(),
                transportationalRequests.getUser().getProfile().getRating(),
                transportationalRequests.getUser().getProfile().getDelivery_count(),
                transportationalRequests.getUser().getProfile().getProfile_image(),
                transportationalRequests.getUser().getProfile().getSubscription()

            ),
            transportationalRequests.getQuantity(),
            transportationalRequests.getStatus(),
            dateToDisplay(transportationalRequests.getDate_posted()));
    } 

    public Page<ListTransportationalRequest> fetchRequestsMade(Integer page, User user) {
        Page<TransportationalRequests> transportationalRequests = transportationalRequestsRepository.findByUser(user, PageRequest.of(page, 10));

        List<ListTransportationalRequest> response = transportationalRequests.getContent().stream()
        .map(val -> new ListTransportationalRequest(
                        val.get_id(),
                        new ProductListing(
                            val.getProduct().get_id(),
                            val.getProduct().getProduct_name(),
                            val.getProduct().getOwner().getProfile(),
                            val.getProduct().getDescription(),
                            val.getProduct().getPrice(),
                            val.getProduct().getQuantity(),
                            val.getProduct().getProduct_image(),
                            val.getProduct().getTransportation_type()
                        ),
                        new Profile(
                            val.getUser().getProfile().get_id(),
                            val.getUser().getProfile().getFull_name(),
                            val.getUser().getProfile().getOrganization(),
                            val.getUser().getProfile().getEmail(),
                            val.getUser().getProfile().getPhone_number(),
                            val.getUser().getProfile().getAddress(),
                            val.getUser().getProfile().getRating(),
                            val.getUser().getProfile().getDelivery_count(),
                            val.getUser().getProfile().getProfile_image(),
                            val.getUser().getProfile().getSubscription()

                        ),
                        val.getQuantity(),
                        val.getStatus(),
                        dateToDisplay(val.getDate_posted())
                        ))
        .collect(Collectors.toList());
        
        return new PageImpl<>(response, PageRequest.of(page, 10), transportationalRequests.getTotalElements());
    }

    public Page<ListTransportationalRequest> searchAllRequests(String searchValue, Integer page) {
        List<TransportationalRequests> requests = transportRequestDao.findRequestsByFullName(searchValue, page);
        List<ListTransportationalRequest> response = requests.stream()
        .map(val -> new ListTransportationalRequest(
                        val.get_id(),
                        new ProductListing(
                            val.getProduct().get_id(),
                            val.getProduct().getProduct_name(),
                            val.getProduct().getOwner().getProfile(),
                            val.getProduct().getDescription(),
                            val.getProduct().getPrice(),
                            val.getProduct().getQuantity(),
                            val.getProduct().getProduct_image(),
                            val.getProduct().getTransportation_type()
                        ),
                        new Profile(
                            val.getUser().getProfile().get_id(),
                            val.getUser().getProfile().getFull_name(),
                            val.getUser().getProfile().getOrganization(),
                            val.getUser().getProfile().getEmail(),
                            val.getUser().getProfile().getPhone_number(),
                            val.getUser().getProfile().getAddress(),
                            val.getUser().getProfile().getRating(),
                            val.getUser().getProfile().getDelivery_count(),
                            val.getUser().getProfile().getProfile_image(),
                            val.getUser().getProfile().getSubscription()
                        ),
                        val.getQuantity(),
                        val.getStatus(),
                        dateToDisplay(val.getDate_posted())
                        ))
        .collect(Collectors.toList());
        return new PageImpl<>(response, PageRequest.of(page,10), requests.size());
    }
}
