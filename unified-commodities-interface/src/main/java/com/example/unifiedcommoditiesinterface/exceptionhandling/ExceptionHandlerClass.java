package com.example.unifiedcommoditiesinterface.exceptionhandling;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import com.mongodb.DuplicateKeyException;

@RestControllerAdvice
public class ExceptionHandlerClass {
    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(Response.SC_BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = { IOException.class })
    public ResponseEntity<?> handleIOException(IOException e) {
        return ResponseEntity.status(Response.SC_BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = { IllegalStateException.class })  
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(Response.SC_BAD_REQUEST).body(e.getMessage());
    }
    
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e)
    {
        return ResponseEntity.status(Response.SC_UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e)
    {
        return ResponseEntity.status(Response.SC_FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<?> handleNullPointerException(NullPointerException e)
    {
        return ResponseEntity.status(Response.SC_NO_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException e)
    {
        return ResponseEntity.status(Response.SC_NO_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<?> runTimeException(AuthenticationException e)
    {
        return ResponseEntity.status(Response.SC_PROXY_AUTHENTICATION_REQUIRED).body(e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handlerunTimeException(RuntimeException e)
    {
        return ResponseEntity.status(Response.SC_EXPECTATION_FAILED).body(e.getMessage());
    }
    
    @ExceptionHandler(value = { SecurityException.class })
    public ResponseEntity<?> handleSecurityException(SecurityException e) {
        return ResponseEntity.status(Response.SC_UNAUTHORIZED).body(e.getMessage());
    }

        
    @ExceptionHandler(value = { MethodNotAllowedException.class })
    public ResponseEntity<?> handleMethodNotAllowedException(MethodNotAllowedException e) {
        return ResponseEntity.status(Response.SC_UNAUTHORIZED).body(e.getMessage());
    }
}
