package com.example.unifiedcommoditiesinterface.services.implementation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.repositories.ProfileRepository;
import com.example.unifiedcommoditiesinterface.repositories.RoleRepository;
import com.example.unifiedcommoditiesinterface.repositories.UserRepository;
import com.example.unifiedcommoditiesinterface.services.SubscriptionService;

@Service
public class SubscriptionServiceImplementation implements SubscriptionService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProfileRepository profileRepository;
    
    public Boolean isValid(User user) {
        
        if(user.getProfile().getSubscription() == null) {
            return false;
        }

        Date currentDate = new Date();
        Boolean valid = currentDate.compareTo(user.getProfile().getSubscription().getEnd_date()) <= 0;
        
        if(!valid){
            Profile profile = user.getProfile();
            profile.setSubscription(null);
            profileRepository.save(profile);

            user.setPermissions(roleRepository.findByAuthority("USER").get().getPermissions());
            userRepository.save(user);
        }
        return valid;
    }
}
