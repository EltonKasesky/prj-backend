package com.prj.prjbackend.modules.user.dto;

import java.util.UUID;

public record UserResponseDTO(
    UUID id,
    String name,
    String email
) { }
