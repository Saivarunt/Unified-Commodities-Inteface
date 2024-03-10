package com.example.unifiedcommoditiesinterface.services.implementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.unifiedcommoditiesinterface.models.Permissions;
import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.Role;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.LoginResponse;
import com.example.unifiedcommoditiesinterface.models.dto.Registration;
import com.example.unifiedcommoditiesinterface.models.dto.RegistrationResponse;
import com.example.unifiedcommoditiesinterface.repositories.ProfileRepository;
import com.example.unifiedcommoditiesinterface.repositories.RoleRepository;
import com.example.unifiedcommoditiesinterface.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;


@Service
@Transactional
public class AuthenticationService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProfileRepository profileRepository;


    public RegistrationResponse registerUser(Registration registration, String role) {
        
        String encodedPassword = passwordEncoder.encode(registration.getPassword());

        Role userRole = roleRepository.findByAuthority("USER").get();

        Role subRole = null;

        Set<Permissions> permissions = new HashSet<>();
        
        Set<Role> authorities = new HashSet<>();

        Profile profile = new Profile(null, "", "", registration.getEmail(), "", null, 0, 0, "", null);
        userRole.getPermissions().stream().forEach(val ->{
            permissions.add(val);
        });

        authorities.add(userRole);

        if(role.equals("SUPPLIER")){
            subRole = roleRepository.findByAuthority("SUPPLIER").get();
            // check subscription and add permissions
        }
        else if(role.equals("CONSUMER")){
            subRole = roleRepository.findByAuthority("CONSUMER").get();
            subRole.getPermissions().stream().forEach(val -> {
                permissions.add(val);
            });
        }
        else if(role.equals("TRANSPORTER")){
            subRole = roleRepository.findByAuthority("TRANSPORTER").get();
            // check subscription and add permissions
        }
        else{
            throw new RuntimeException("Invalid role details.");
        }

        if(subRole != null){
            authorities.add(subRole);
        }

        User user = userRepository.save(new User(null,registration.getUsername(), encodedPassword, authorities, permissions, profileRepository.save(profile)));
        return new RegistrationResponse(user.getUsername(), user.getProfile());

    }

    public LoginResponse loginUser(Registration registration) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(registration.getUsername(), registration.getPassword())
        );

        String token = tokenService.generateJWT(auth);
        return new LoginResponse(userRepository.findByUsername(registration.getUsername()).get(), token);
    }

    public User fetchUserFromToken(HttpServletRequest request) {
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        return userRepository.findByUsername(username).get();
    }
}
