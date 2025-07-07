package com.project.MyBank.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

        @NotBlank(message= "nome é obrigatório")
        String name,

        @NotBlank(message = "email é obrigatório")
        @Email(message = "email inválido")
        String email,

        @Size(min = 4, message = "Senha deve ter no mínimo 4 caracteres")
        @NotBlank(message = "senha é obrigatória")
        String password
) {
}
