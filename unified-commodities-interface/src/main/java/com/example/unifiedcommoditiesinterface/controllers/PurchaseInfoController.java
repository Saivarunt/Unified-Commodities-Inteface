package com.example.unifiedcommoditiesinterface.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.unifiedcommoditiesinterface.models.PurchaseInfo;
import com.example.unifiedcommoditiesinterface.models.dto.PurchaseRequest;
import com.example.unifiedcommoditiesinterface.models.dto.PurchaseResponse;
import com.example.unifiedcommoditiesinterface.services.PurchaseInfoService;
import com.example.unifiedcommoditiesinterface.services.UserService;
import com.example.unifiedcommoditiesinterface.services.implementation.AuthenticationService;
import com.example.unifiedcommoditiesinterface.services.implementation.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("purchase")
@CrossOrigin(origins = "http://localhost:4200/")
public class PurchaseInfoController {
        
    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    PurchaseInfoService purchaseInfoService;

    @Autowired
    AuthenticationService authenticationService;

    @PreAuthorize("@permissionsService.hasAccess('PURCHASE_PRODUCTS')")
    @PostMapping("/make-purchase")
    public ResponseEntity<PurchaseResponse> buyProduct(@RequestBody PurchaseRequest purchaseRequest, HttpServletRequest request) {
        return new ResponseEntity<PurchaseResponse>(purchaseInfoService.purchaseProduct(purchaseRequest, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }

    // get all purchase info
    @GetMapping("/")
    public ResponseEntity<Page<PurchaseInfo>> fetchAllPurchases(@RequestParam Integer page) {
        return new ResponseEntity<>(purchaseInfoService.findAllPurchaseInfo(page), HttpStatus.OK);
    }

}
