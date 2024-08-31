package com.test.reme.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "Este campo é requerido")
        String email,
        @NotNull(message = "Este campo é requerido")
        @Size(max = 2 , min = 1)
        String password){
}
