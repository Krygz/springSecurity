package com.test.reme.dto;

import javax.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull(message = "Este campo é requerido")
        String name,
        @NotNull(message = "Este campo é requerido")
        String email,
        @NotNull(message = "Este campo é requerido")
        String password)
{
}
