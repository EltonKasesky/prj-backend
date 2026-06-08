package com.prj.prjbackend.infra.exception.dto;

import java.time.LocalDateTime;

public record StandardErrorDTO(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message
) { }
