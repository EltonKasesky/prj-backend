package com.prj.prjbackend.modules.album.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AlbumUpdateRequestDTO(
        @NotBlank(message = "O título não pode ser vazio ou nulo.")
        @Schema(example = "Copa do Mundo FIFA 2026")
        String title,

        @NotNull(message = "O total de páginas não pode ser vazio ou nulo.")
        @Positive(message = "O total de páginas deve ser maior que zero.")
        @Schema(example = "112")
        Integer totalPages,

        @NotNull(message = "O total de figurinhas não pode ser vazio ou nulo.")
        @Positive(message = "O total de figurinhas deve ser maior que zero.")
        @Schema(example = "980")
        Integer totalStickers,

        @Schema(type = "string", format = "byte", example = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAAB")
        byte[] coverImage
) {
}
