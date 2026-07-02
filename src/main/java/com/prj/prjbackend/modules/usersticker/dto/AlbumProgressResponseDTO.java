package com.prj.prjbackend.modules.usersticker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AlbumProgressResponseDTO(
        @Schema(example = "245")
        Integer collected,

        @Schema(example = "980")
        Integer total,

        @Schema(example = "25.0")
        Double percentage
) {
}
