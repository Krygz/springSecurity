package com.test.reme.controller;

import com.test.reme.dto.LoginRequest;
import com.test.reme.dto.LoginResponse;
import com.test.reme.dto.RegisterRequest;
import com.test.reme.dto.UserResponse;
import com.test.reme.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody @Valid LoginRequest request){
       var user = authenticationService.login(request);
       return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register (@RequestBody @Valid RegisterRequest request){
      UserResponse newUser = authenticationService.register(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
