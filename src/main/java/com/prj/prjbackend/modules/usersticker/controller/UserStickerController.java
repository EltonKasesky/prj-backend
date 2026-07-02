package com.prj.prjbackend.modules.usersticker.controller;

import com.prj.prjbackend.modules.usersticker.dto.AcquireStickerRequestDTO;
import com.prj.prjbackend.modules.usersticker.dto.AlbumProgressResponseDTO;
import com.prj.prjbackend.modules.usersticker.dto.UserStickerResponseDTO;
import com.prj.prjbackend.modules.usersticker.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Coleção de Figurinhas", description = "Endpoints para gerenciamento da coleção de figurinhas do usuário autenticado")
@RestController
@RequestMapping("/user-stickers")
@RequiredArgsConstructor
public class UserStickerController {
    private final ListUserStickerUseCase listUserStickerUseCase;
    private final GetUserStickerUseCase getUserStickerUseCase;
    private final AcquireStickerUseCase acquireStickerUseCase;
    private final RemoveStickerUseCase removeStickerUseCase;
    private final GetCollectionSummaryUseCase getCollectionSummaryUseCase;
    private final ClearUserStickerCollectionUseCase clearUserStickerCollectionUseCase;

    @GetMapping
    @Operation(
            summary = "Lista todas as figurinhas do usuário autenticado.",
            description = "Retorna todas as figurinhas pertencentes à coleção do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Coleção retornada com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            [
                              {
                                "id": "1e6a9d3c-df3c-4b8a-9a8d-2f8d3b6a9c11",
                                "tag": "9e107d9d372bb6826bd81d3542a419d6",
                                "name": "Lionel Messi",
                                "quantity": 2
                              }
                            ]
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário desativado.")
    })
    public ResponseEntity<List<UserStickerResponseDTO>> getAll() {
        return ResponseEntity.ok(listUserStickerUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca uma figurinha da coleção.",
            description = "Retorna uma figurinha pertencente à coleção do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Figurinha retornada com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "id": "1e6a9d3c-df3c-4b8a-9a8d-2f8d3b6a9c11",
                              "tag": "9e107d9d372bb6826bd81d3542a419d6",
                              "name": "Lionel Messi",
                              "quantity": 2
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Figurinha não encontrada na coleção.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Figurinha não encontrada na coleção."
                            }
                            """))
            )
    })
    public ResponseEntity<UserStickerResponseDTO> getById(
            @Parameter(description = "Identificador da figurinha na coleção")
            @PathVariable UUID id) {

        return ResponseEntity.ok(getUserStickerUseCase.execute(id));
    }

    @PostMapping
    @PreAuthorize("@securityUtils.isValidCollector()")
    @Operation(
            summary = "Adquire uma figurinha.",
            description = "Adiciona uma figurinha à coleção do usuário autenticado. Caso ela já exista, incrementa sua quantidade. **Acesso restrito ao perfil Colecionador.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Figurinha adquirida com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "id": "1e6a9d3c-df3c-4b8a-9a8d-2f8d3b6a9c11",
                              "tag": "9e107d9d372bb6826bd81d3542a419d6",
                              "name": "Lionel Messi",
                              "quantity": 1
                            }
                            """))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 400,
                              "error": "Dados inválidos.",
                              "message": "tag: A tag não pode ser vazia ou nula."
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de Colecionador."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Figurinha não encontrada.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Sticker não encontrada."
                            }
                            """))
            )
    })
    public ResponseEntity<UserStickerResponseDTO> acquireSticker(
            @Valid @RequestBody AcquireStickerRequestDTO request) {

        UserStickerResponseDTO response =
                acquireStickerUseCase.execute(request);

        return ResponseEntity
                .created(URI.create("/user-stickers/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidCollector()")
    @Operation(
            summary = "Remove uma figurinha da coleção.",
            description = "Remove uma unidade da figurinha da coleção. Caso seja a última unidade, remove o registro da coleção. **Acesso restrito ao perfil Colecionador.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Figurinha removida com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de Colecionador."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Figurinha não encontrada na coleção.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Figurinha não encontrada na coleção."
                            }
                            """))
            )
    })
    public ResponseEntity<Void> removeSticker(
            @Parameter(description = "Identificador da figurinha na coleção")
            @PathVariable UUID id) {

        removeStickerUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("@securityUtils.isValidCollector()")
    @Operation(
            summary = "Limpa a coleção do usuário autenticado.",
            description = "Remove todas as figurinhas da coleção do usuário autenticado, sem afetar o catálogo nem a coleção de outros usuários. **Acesso restrito ao perfil Colecionador.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Coleção limpa com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de Colecionador.")
    })
    public ResponseEntity<Void> clearCollection() {
        clearUserStickerCollectionUseCase.execute();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/collection-summary")
    @Operation(
            summary = "Resumo da coleção.",
            description = "Retorna o progresso da coleção do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Resumo retornado com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "collected": 245,
                              "total": 980,
                              "percentage": 25.0
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado.")
    })
    public ResponseEntity<AlbumProgressResponseDTO> getCollectionSummary() {

        return ResponseEntity.ok(getCollectionSummaryUseCase.execute());
    }
}