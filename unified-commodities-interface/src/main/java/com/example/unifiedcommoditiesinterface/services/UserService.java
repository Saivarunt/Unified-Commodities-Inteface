package com.example.unifiedcommoditiesinterface.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.User;

@Service
public interface UserService {
    public User getUserByUsername(String username);
    public Page<User> findAllUsers(Integer page);
    public List<User> fetchAllUsers();
    public Page<User> fetchByUsername(String username, Integer page);
}
