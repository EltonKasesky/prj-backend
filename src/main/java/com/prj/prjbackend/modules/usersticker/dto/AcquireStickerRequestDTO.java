package com.prj.prjbackend.modules.usersticker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AcquireStickerRequestDTO(
        @NotBlank(message = "A tag não pode ser vazia ou nula.")
        @Schema(example = "9e107d9d372bb6826bd81d3542a419d6")
        String tag
) {
}
