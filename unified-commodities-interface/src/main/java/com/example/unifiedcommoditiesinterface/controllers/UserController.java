package com.example.unifiedcommoditiesinterface.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.ProfileInformation;
import com.example.unifiedcommoditiesinterface.services.ProfileService;
import com.example.unifiedcommoditiesinterface.services.UserService;
import com.example.unifiedcommoditiesinterface.services.implementation.AuthenticationService;
import com.example.unifiedcommoditiesinterface.services.implementation.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/profile")
    public ResponseEntity<Profile> editProfile(@ModelAttribute ProfileInformation body, HttpServletRequest request) throws IOException {
        return new ResponseEntity<Profile>(profileService.saveProfile(authenticationService.fetchUserFromToken(request), body), HttpStatus.OK);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<Profile> viewProfile(@PathVariable String username) {
        return new ResponseEntity<Profile>(profileService.getUserByUsername(username), HttpStatus.OK);
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<User> fetchByUsername(@PathVariable String username) {
        return new ResponseEntity<User>(userService.getUserByUsername(username), HttpStatus.OK);
    }
    
    // get all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Page<User>> fetchAllUsers(@RequestParam Integer page) {
        return new ResponseEntity<Page<User>>(userService.findAllUsers(page), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchByUsername(@RequestParam String username, @RequestParam Integer page) {
        return new ResponseEntity<Page<User>>(userService.fetchByUsername(username, page), HttpStatus.OK);
    }
}
