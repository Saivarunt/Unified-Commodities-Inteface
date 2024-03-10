package com.example.unifiedcommoditiesinterface.models.dto;



import com.example.unifiedcommoditiesinterface.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private User user;
    private String jwt;
}
