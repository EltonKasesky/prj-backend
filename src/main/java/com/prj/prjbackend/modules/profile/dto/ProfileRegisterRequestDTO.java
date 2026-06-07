package com.prj.prjbackend.modules.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfileRegisterRequestDTO(
        @NotBlank(message = "O nome não pode ser vazio ou nulo.")
        @Size(min = 2, max = 100, message = "O perfil deve conter entre 2 e 100 caracteres.")
        String name
) { }
