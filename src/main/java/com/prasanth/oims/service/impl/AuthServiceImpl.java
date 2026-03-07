package com.prasanth.oims.service.impl;

import java.time.LocalDateTime;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prasanth.oims.dto.LoginRequest;
import com.prasanth.oims.dto.RegisterRequest;
import com.prasanth.oims.entity.Role;
import com.prasanth.oims.entity.User;
import com.prasanth.oims.repository.UserRepository;
import com.prasanth.oims.service.AuthService;
import com.prasanth.oims.util.JwtUtil;

import org.springframework.security.authentication.BadCredentialsException;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }   

    @Override
    @Transactional
    public String register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalStateException("Email already registered");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.USER);
        newUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(newUser);

        return "User registered successfully";
        
    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> 
                            new BadCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getId(), user.getRole());
        
    }




}
