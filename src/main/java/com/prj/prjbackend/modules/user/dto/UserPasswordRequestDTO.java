package com.prj.prjbackend.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordRequestDTO(
        @NotBlank(message = "A senha não pode ser vazia ou nula.")
        @Size(min = 8, max = 255, message = "A senha deve conter entre 8 e 255 caracteres")
        String password
) { }
