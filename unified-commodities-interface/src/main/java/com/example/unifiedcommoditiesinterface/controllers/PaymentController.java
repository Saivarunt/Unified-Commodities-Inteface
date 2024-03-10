package com.example.unifiedcommoditiesinterface.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.unifiedcommoditiesinterface.models.TransactionDetails;
import com.example.unifiedcommoditiesinterface.services.implementation.PaymentService;

@RestController
@RequestMapping("payment")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/create-transaction/{amount}")
    public ResponseEntity<TransactionDetails> createTransaction(@PathVariable double amount) {
        try {
            TransactionDetails response = paymentService.createTransaction(amount);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
