package com.prasanth.oims.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasanth.oims.dto.LoginRequest;
import com.prasanth.oims.dto.RegisterRequest;
import com.prasanth.oims.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @Operation(summary = "Register a new user",security = {})
    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequest request){
        String response = authService.register(request);
        return response;
    }

    @Operation(summary = "Login a user",security = {})
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest request){
        String response = authService.login(request);
        return  response;
    }
}
