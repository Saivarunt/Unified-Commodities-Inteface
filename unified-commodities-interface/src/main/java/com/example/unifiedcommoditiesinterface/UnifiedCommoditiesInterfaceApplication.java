package com.example.unifiedcommoditiesinterface;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.unifiedcommoditiesinterface.models.Permissions;
import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.Role;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.repositories.PermissionsRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProfileRepository;
import com.example.unifiedcommoditiesinterface.repositories.RoleRepository;
import com.example.unifiedcommoditiesinterface.repositories.UserRepository;

@SpringBootApplication
@EnableScheduling
public class UnifiedCommoditiesInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnifiedCommoditiesInterfaceApplication.class, args);
	}
@Bean
	CommandLineRunner run(RoleRepository roleRepositoy, UserRepository userRepository, PasswordEncoder passwordEncoder, PermissionsRepository permissionsRepository, 
	ProfileRepository profileRepository) {
		return args ->{

			if(roleRepositoy.findByAuthority("ADMIN").isPresent()){
				return;
			}

			permissionsRepository.save(new Permissions(null, "READ_PRODUCTS"));
			permissionsRepository.save(new Permissions(null, "VIEW_PROFILE"));
			permissionsRepository.save(new Permissions(null, "EDIT_PROFILE"));
			permissionsRepository.save(new Permissions(null, "EDIT_PRODUCTS"));
			permissionsRepository.save(new Permissions(null, "DELETE_PRODUCTS"));
			permissionsRepository.save(new Permissions(null, "RATE_PRODUCTS"));
			permissionsRepository.save(new Permissions(null, "RATE_TRANSPORTER"));
			permissionsRepository.save(new Permissions(null, "SUBSCRIBE_PRODUCTS_LISTING"));
			permissionsRepository.save(new Permissions(null, "SUBSCRIBE_TRANSPORTER_LISTING"));
			permissionsRepository.save(new Permissions(null, "MAKE_TRANSPORTATION_REQUEST"));
			permissionsRepository.save(new Permissions(null, "MAKE_TRANSPORTATION_PROPOSAL"));
			permissionsRepository.save(new Permissions(null, "ACCEPT_OR_REJECT_TRANSPORT_REQUEST"));
			permissionsRepository.save(new Permissions(null, "VIEW_TRANSPORT_REQUESTS"));
			permissionsRepository.save(new Permissions(null, "PURCHASE_PRODUCTS"));


			Set<Permissions> supplierPermissions = new HashSet<>();
			Set<Permissions> consumerPermissions = new HashSet<>();
			Set<Permissions> transporterPermissions = new HashSet<>();
			Set<Permissions> adminPermissions = new HashSet<>();
			Set<Permissions> userPermissions = new HashSet<>();


			permissionsRepository.findAll().stream().forEach(val -> {
				adminPermissions.add(val);

				if(val.getPermission().equals("READ_PRODUCTS") || val.getPermission().equals("VIEW_PROFILE") ||
				val.getPermission().equals("EDIT_PROFILE")){
					userPermissions.add(val);
				}
				else if(val.getPermission().equals("EDIT_PRODUCTS") || val.getPermission().equals("DELETE_PRODUCTS") || 
				val.getPermission().equals("SUBSCRIBE_PRODUCTS_LISTING")){
					supplierPermissions.add(val);
				}
				else if(val.getPermission().equals("RATE_TRANSPORTER") || val.getPermission().equals("MAKE_TRANSPORTATION_REQUEST") ||
				val.getPermission().equals("ACCEPT_OR_REJECT_TRANSPORT_REQUEST")){
					supplierPermissions.add(val);
					consumerPermissions.add(val);
				}
				else if(val.getPermission().equals("SUBSCRIBE_TRANSPORTER_LISTING") || val.getPermission().equals("VIEW_TRANSPORT_REQUESTS") ||
				val.getPermission().equals("MAKE_TRANSPORTATION_PROPOSAL")){
					transporterPermissions.add(val);
				}
				else if(val.getPermission().equals("RATE_PRODUCTS") || val.getPermission().equals("PURCHASE_PRODUCTS") ){
					consumerPermissions.add(val);
				}
			});

			Role adminRole = roleRepositoy.save(new Role(null,"ADMIN", adminPermissions));
			roleRepositoy.save(new Role(null,"USER",userPermissions));
			roleRepositoy.save(new Role(null,"SUPPLIER",supplierPermissions));
			roleRepositoy.save(new Role(null,"CONSUMER",consumerPermissions));
			roleRepositoy.save(new Role(null,"TRANSPORTER",transporterPermissions));
			

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			Profile profile = new Profile(null, "Admin", "UCI", "admin@gmail.com", "1234567890", null, 0, 0, "", null);
			profileRepository.save(profile);

			User admin = new User(null, "admin", passwordEncoder.encode("admin"), roles, adminPermissions, profile);

			userRepository.save(admin);
			
		};
	}
}
