package com.test.reme.dto;

import java.util.UUID;

public record UserResponse(
        UUID id ,
        String name,
        String email
){
}
