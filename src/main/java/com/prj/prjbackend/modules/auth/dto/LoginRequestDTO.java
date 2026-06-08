package com.prj.prjbackend.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "O email não pode ser vazio ou nulo.")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "A senha não pode ser vazia ou nula.")
        String password
) {
}
