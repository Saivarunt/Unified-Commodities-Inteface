package com.example.unifiedcommoditiesinterface.services;

import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.User;

@Service
public interface SubscriptionService {
    public Boolean isValid(User user);
}
