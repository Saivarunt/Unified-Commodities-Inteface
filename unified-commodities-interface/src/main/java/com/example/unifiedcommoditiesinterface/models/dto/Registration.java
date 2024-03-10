package com.example.unifiedcommoditiesinterface.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Registration {
    private String username;
    private String email;
    private String password;
}
