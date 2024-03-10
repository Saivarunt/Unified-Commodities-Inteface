package com.example.unifiedcommoditiesinterface.models;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority{
    @Id
    private String _id;

    private String authority;

    public Role(String authority){
        this.authority = authority;
    }

    @DocumentReference
    public Set<Permissions> permissions; 
}
