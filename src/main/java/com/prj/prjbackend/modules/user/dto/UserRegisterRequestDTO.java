package com.prj.prjbackend.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
    @Size(min = 2, max = 100, message = "O nome deve conter entre 2 e 100 caracteres.")
    String name,

    @Email(message = "O email informado é invalido.")
    String email,

    @Size(min = 8, max = 255, message = "A senha deve conter entre 8 e 255 caracteres")
    String password
) { }
