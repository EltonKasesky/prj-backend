package com.prj.prjbackend.modules.usersticker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserStickerResponseDTO(
        @Schema(example = "8f14e45f-ceea-167a-5a36-dedd4bea2543")
        UUID id,

        @Schema(example = "9e107d9d372bb6826bd81d3542a419d6")
        String tag,

        @Schema(example = "Lionel Messi")
        String name,

        @Schema(example = "2")
        Integer quantity
) {
}
