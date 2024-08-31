package com.test.reme.service;

import com.test.reme.dto.LoginRequest;
import com.test.reme.dto.LoginResponse;
import com.test.reme.dto.RegisterRequest;
import com.test.reme.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
   LoginResponse login (LoginRequest loginRequest);
   UserResponse register (RegisterRequest request);
}
