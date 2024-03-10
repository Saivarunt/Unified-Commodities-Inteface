package com.example.unifiedcommoditiesinterface.services.implementation;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.Payment;
import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.Subscription;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.ProfileInformation;
import com.example.unifiedcommoditiesinterface.repositories.PaymentRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProfileRepository;
import com.example.unifiedcommoditiesinterface.repositories.RoleRepository;
import com.example.unifiedcommoditiesinterface.repositories.SubscriptionRepository;
import com.example.unifiedcommoditiesinterface.repositories.UserRepository;
import com.example.unifiedcommoditiesinterface.services.ProfileService;

@Service
public class ProfileServiceImplementation implements ProfileService{
    
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    SubscriptionServiceImplementation subscriptionService;

    public Profile saveProfile(User user, ProfileInformation profileInformation) throws IOException {
        Profile profile = user.getProfile();
        
        profile.setFull_name(profileInformation.getFull_name());
        profile.setOrganization(profileInformation.getOrganization());
        profile.setEmail(user.getProfile().getEmail());
        profile.setPhone_number(profileInformation.getPhone_number());
        profile.setAddress(profileInformation.getAddress());
        
        if(!profileInformation.getProfile_image().isEmpty()){
            profile.setProfile_image(imageService.imageUpload(profileInformation.getProfile_image(), user.getUsername()));
        }

        if(profileInformation.getPeriod() != null && profileInformation.getSubscription_type() != null) {
            if((!profileInformation.getPeriod().equals("") && !profileInformation.getSubscription_type().equals(""))){
                Date startDate = new Date();
                Date endDate = new Date();
                
                if(profileInformation.getPeriod().equals("Monthly")){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, 30);
                    endDate = c.getTime();
                }
                else if(profileInformation.getPeriod().equals("Yearly")){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, 365);
                    endDate = c.getTime();
                }
                
                
                if(user.getProfile().getSubscription() == null && profileInformation.getRazorpay_payment_id() != null && profileInformation.getRazorpay_order_id() != null && profileInformation.getRazorpay_signature() != null){
                    Payment payment_info = new Payment(null, profileInformation.getRazorpay_payment_id(), profileInformation.getRazorpay_order_id(), profileInformation.getRazorpay_signature(), Double.parseDouble(profileInformation.getAmount()));
                    Payment saved_payment = paymentRepository.save(payment_info);
    
                    Subscription subscription = new Subscription(null, profileInformation.getSubscription_type(), profileInformation.getPeriod(), startDate, endDate, saved_payment);
                    
                    profile.setSubscription(subscriptionRepository.save(subscription));
                    
                    user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).forEach(val ->{
                        roleRepository.findAll().stream().forEach(role->{
                    
                            if(val.equals("SUPPLIER") && role.getAuthority().equals(val)){
                                user.getPermissions().addAll(role.getPermissions());         
                            }            
                            else if(val.equals("TRANSPORTER") && role.getAuthority().equals(val)){
                                user.getPermissions().addAll(role.getPermissions());         
                            }
                    
                        });
                    });
        
                    userRepository.save(user);
                }
            }
            else{
                profile.setSubscription(null);
            }
        }

        return profileRepository.save(profile);
    } 

    public Profile getUserByUsername(String username) {
        return userRepository.findByUsername(username).get().getProfile();
    }
}