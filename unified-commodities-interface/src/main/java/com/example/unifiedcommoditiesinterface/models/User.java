package com.example.unifiedcommoditiesinterface.models;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User  implements UserDetails{

    @Id
    private String _id;

    @Indexed(unique = true)
    private String username;

    private String password;

    @DocumentReference
    private Set<Role> authorities;

    @DocumentReference
    private Set<Permissions> permissions;

    @DocumentReference
    private Profile profile;
    

    public User addAuthorities(Role authority) {
        this.authorities.add(authority);
        return this;
    }

    public User updateAuthorities(Role authority) {
        this.authorities.remove(authority);
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
