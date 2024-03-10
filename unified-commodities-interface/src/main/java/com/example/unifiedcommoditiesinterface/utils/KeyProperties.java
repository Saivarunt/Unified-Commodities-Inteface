package com.example.unifiedcommoditiesinterface.utils;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class KeyProperties {
    
    @Getter
    @Setter
    private RSAPublicKey publicKey;

    @Getter
    @Setter
    private RSAPrivateKey privateKey;

    public KeyProperties() {
        KeyPair pair = KeyGeneratorUtility.generateRsaKey();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }
}
