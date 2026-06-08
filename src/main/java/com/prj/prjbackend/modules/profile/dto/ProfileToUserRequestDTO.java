package com.prj.prjbackend.modules.profile.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ProfileToUserRequestDTO(
        UUID userId,

        @NotBlank(message = "O nome do perfil não pode ser vazio ou nulo.")
        String profileName
) { }
