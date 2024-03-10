package com.example.unifiedcommoditiesinterface.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.Products;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.ListTransportationalRequest;

@Service
public interface TransportationalRequestsService {
    public String makeRequest(Products product, User user ,String transportational_type, Double quantity);
    public Page<ListTransportationalRequest> fetchTransportationalRequests(Integer page);
    public Page<ListTransportationalRequest> fetchRequestsMade(Integer page, User user);
    public ListTransportationalRequest fetchTransportationalRequestsById(String _id);
}
