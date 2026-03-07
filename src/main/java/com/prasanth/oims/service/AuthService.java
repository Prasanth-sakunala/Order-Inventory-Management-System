package com.prasanth.oims.service;

import com.prasanth.oims.dto.LoginRequest;
import com.prasanth.oims.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
}
