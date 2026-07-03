package com.prj.prjbackend.modules.sticker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StickerUpdateRequestDTO(
        @NotBlank(message = "O nome não pode ser vazio ou nulo.")
        @Schema(example = "Lionel Messi")
        String name,

        @NotNull(message = "A página não pode ser vazia ou nula.")
        @Positive(message = "A página deve ser maior que zero.")
        @Schema(example = "1")
        Integer page,

        @NotNull(message = "O número da figurinha não pode ser vazio ou nulo.")
        @Positive(message = "O número da figurinha deve ser maior que zero.")
        @Schema(example = "10")
        Integer number,

        @Schema(example = "Camisa 10 da seleção Argentina")
        String description,

        @Schema(type = "string", format = "byte", example = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAAB")
        byte[] image
){
}
