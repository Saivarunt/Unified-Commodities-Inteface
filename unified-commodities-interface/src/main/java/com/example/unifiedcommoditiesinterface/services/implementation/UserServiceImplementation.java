package com.example.unifiedcommoditiesinterface.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.repositories.UserRepository;
import com.example.unifiedcommoditiesinterface.services.UserService;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is Not Found"));
    }
    
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user == null){
            return null;
        }
        return user.get();
    }

    public Page<User> findAllUsers(Integer page) {
        return userRepository.findAll(PageRequest.of(page, 30));
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

}
