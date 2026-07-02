package com.prj.prjbackend.modules.album.dto;

import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record AlbumDetailsDTO(
        @Schema(example = "30eda801-66b8-4902-a0fc-38a6e692702d")
        UUID id,

        @Schema(example = "Copa do Mundo FIFA 2026")
        String title,

        @Schema(example = "/album/cover-image")
        String coverImageUrl,

        @Schema(example = "112")
        Integer totalPages,

        @Schema(example = "980")
        Integer totalStickers,

        List<StickerResponseDTO> stickers
)  {
}
