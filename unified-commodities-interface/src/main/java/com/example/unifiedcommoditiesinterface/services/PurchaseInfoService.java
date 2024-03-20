package com.example.unifiedcommoditiesinterface.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.PurchaseInfo;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.PurchaseRequest;
import com.example.unifiedcommoditiesinterface.models.dto.PurchaseResponse;

@Service
public interface PurchaseInfoService {
    public PurchaseResponse purchaseProduct(PurchaseRequest purchaseRequest, User user);
    public Page<PurchaseInfo> findAllPurchaseInfo(Integer page);
    public Page<PurchaseInfo> searchByProduct(String productName, Integer page);
}
