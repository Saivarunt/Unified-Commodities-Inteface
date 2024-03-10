package com.example.unifiedcommoditiesinterface.models.dto;

import com.example.unifiedcommoditiesinterface.models.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationResponse {
    private String username;
    private Profile profile;
}
