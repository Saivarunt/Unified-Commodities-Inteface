package com.example.unifiedcommoditiesinterface.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.ProfileInformation;

@Service
public interface ProfileService {
    public Profile saveProfile(User user, ProfileInformation profileInformation) throws IOException;
    public Profile getUserByUsername(String username);
}
