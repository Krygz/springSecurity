package com.test.reme.dto;

public record LoginResponse (
        String accessToken,
        Long expiresIn
){
}
