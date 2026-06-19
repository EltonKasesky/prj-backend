package com.prj.prjbackend.modules.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ProfileToUserRequestDTO(
        @NotEmpty(message = "A lista de perfis não pode estar vazia.")
        List<@NotBlank(message = "O nome do perfil não pode ser vazio.") String> profileNames
) { }
