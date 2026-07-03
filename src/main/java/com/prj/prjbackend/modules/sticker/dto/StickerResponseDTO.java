package com.prj.prjbackend.modules.sticker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record StickerResponseDTO(
        @Schema(example = "8f14e45f-ceea-167a-5a36-dedd4bea2543")
        UUID id,

        @Schema(example = "Lionel Messi")
        String name,

        @Schema(example = "1")
        Integer page,

        @Schema(example = "10")
        Integer number,

        @Schema(example = "9e107d9d372bb6826bd81d3542a419d6")
        String tag,

        @Schema(example = "Camisa 10 da seleção Argentina")
        String description,

        @Schema(example = "/stickers/8f14e45f-ceea-167a-5a36-dedd4bea2543/image")
        String imageUrl,

        @Schema(example = "2026-06-30T12:00:00")
        LocalDateTime createdAt,

        @Schema(example = "30eda801-66b8-4902-a0fc-38a6e692702d")
        UUID albumId
) {
}
