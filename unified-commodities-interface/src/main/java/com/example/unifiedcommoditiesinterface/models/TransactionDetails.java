package com.example.unifiedcommoditiesinterface.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetails {
    @Id
    private String id;
    private String orderId;
    private String currency;
    private Integer amount;
    private String key;
}
