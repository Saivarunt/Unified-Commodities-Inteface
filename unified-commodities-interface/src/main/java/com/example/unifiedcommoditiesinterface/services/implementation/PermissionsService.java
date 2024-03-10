package com.example.unifiedcommoditiesinterface.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.repositories.UserRepository;

@Service("permissionsService")
public class PermissionsService {
    
    @Autowired
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(PermissionsService.class);
    Authentication authentication;

    public Boolean hasAccess(String permission) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        Boolean canAccess = user.getPermissions().stream().anyMatch(val -> val.getPermission().equalsIgnoreCase(permission));
        return canAccess;
    }
}
