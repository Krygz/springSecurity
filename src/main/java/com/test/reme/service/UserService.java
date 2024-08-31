package com.test.reme.service;

import com.test.reme.entity.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    User getByEmail(String email);
    User updateAccount(User user , UUID uuid , JwtAuthenticationToken token);
    void deleteByUUID(UUID uuid , JwtAuthenticationToken token);
}
