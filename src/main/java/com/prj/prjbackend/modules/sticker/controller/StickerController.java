package com.prj.prjbackend.modules.sticker.controller;

import com.prj.prjbackend.modules.sticker.dto.StickerImageDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerRegisterRequestDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerUpdateRequestDTO;
import com.prj.prjbackend.modules.sticker.service.*;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@Tag(name = "Figurinhas", description = "Endpoints para consulta e gerenciamento do catálogo de figurinhas")
@RestController
@RequestMapping("/stickers")
@RequiredArgsConstructor
public class StickerController {

    private final GetAllStickersUseCase getAllStickersUseCase;
    private final GetFilteredStickersUseCase getFilteredStickersUseCase;
    private final GetStickerByIdUseCase getStickerByIdUseCase;
    private final GetStickerImageUseCase getStickerImageUseCase;
    private final CreateStickerUseCase createStickerUseCase;
    private final UpdateStickerUseCase updateStickerUseCase;
    private final DeleteStickerUseCase deleteStickerUseCase;
    private final ClearStickerCatalogUseCase clearStickerCatalogUseCase;

    @GetMapping
    @Operation(
            summary = "Lista o catálogo de figurinhas de forma paginada.",
            description = "Retorna uma lista paginada das figurinhas cadastradas no álbum, com filtro opcional por nome, página do álbum e tag. Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de figurinhas obtida com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "content": [
                                {
                                  "id": "8f14e45f-ceea-167a-5a36-dedd4bea2543",
                                  "name": "Lionel Messi",
                                  "page": 1,
                                  "number": 10,
                                  "tag": "9e107d9d372bb6826bd81d3542a419d6",
                                  "description": "Camisa 10 da seleção Argentina",
                                  "imageUrl": "/stickers/8f14e45f-ceea-167a-5a36-dedd4bea2543/image",
                                  "createdAt": "2026-06-30T12:00:00",
                                  "albumId": "30eda801-66b8-4902-a0fc-38a6e692702d"
                                }
                              ],
                              "totalElements": 1,
                              "totalPages": 1,
                              "number": 0,
                              "size": 10
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente.")
    })
    public ResponseEntity<Page<StickerResponseDTO>> list(
            @Parameter(description = "Número da página de resultados (inicia em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página de resultados")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filtro por parte do nome da figurinha")
            @RequestParam(required = false) String name,
            @Parameter(description = "Filtro pela página do álbum em que a figurinha aparece")
            @RequestParam(required = false) Integer stickerPage,
            @Parameter(description = "Filtro por parte da tag (hash MD5) da figurinha")
            @RequestParam(required = false) String tag
    ) {
        boolean hasFilter = name != null || stickerPage != null || tag != null;

        Page<StickerResponseDTO> stickers = hasFilter
                ? getFilteredStickersUseCase.execute(page, size, name, stickerPage, tag)
                : getAllStickersUseCase.execute(page, size);

        return ResponseEntity.ok(stickers);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtém os detalhes de uma figurinha do catálogo.",
            description = "Retorna os dados de uma figurinha específica. Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Figurinha obtida com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "id": "8f14e45f-ceea-167a-5a36-dedd4bea2543",
                              "name": "Lionel Messi",
                              "page": 1,
                              "number": 10,
                              "tag": "9e107d9d372bb6826bd81d3542a419d6",
                              "description": "Camisa 10 da seleção Argentina",
                              "imageUrl": "/stickers/8f14e45f-ceea-167a-5a36-dedd4bea2543/image",
                              "createdAt": "2026-06-30T12:00:00",
                              "albumId": "30eda801-66b8-4902-a0fc-38a6e692702d"
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Figurinha não encontrada.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Figurinha não encontrada com o ID fornecido: 8f14e45f-ceea-167a-5a36-dedd4bea2543"
                            }
                            """))
            )
    })
    public ResponseEntity<StickerResponseDTO> getById(
            @Parameter(description = "ID da figurinha a ser buscada")
            @PathVariable UUID id
    ) {
        StickerResponseDTO sticker = getStickerByIdUseCase.execute(id);
        return ResponseEntity.ok(sticker);
    }

    @GetMapping("/{id}/image")
    @Operation(
            summary = "Obtém a imagem de uma figurinha.",
            description = "Retorna os bytes da imagem da figurinha. Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Imagem obtida com sucesso.",
                    content = @Content(mediaType = "image/*")
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Figurinha não encontrada.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Figurinha não encontrada com o ID fornecido: 8f14e45f-ceea-167a-5a36-dedd4bea2543"
                            }
                            """))
            )
    })
    public ResponseEntity<byte[]> getImage(
            @Parameter(description = "ID da figurinha cuja imagem será buscada")
            @PathVariable UUID id
    ) {
        StickerImageDTO image = getStickerImageUseCase.execute(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.imageType()))
                .body(image.image());
    }

    @PostMapping
    @PreAuthorize("@securityUtils.isValidAuthor()")
    @Operation(
            summary = "Cadastra uma nova figurinha no catálogo.",
            description = "Cria uma nova figurinha vinculada ao álbum oficial. **Acesso restrito ao perfil Autor.** Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Figurinha criada com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "id": "8f14e45f-ceea-167a-5a36-dedd4bea2543",
                              "name": "Lionel Messi",
                              "page": 1,
                              "number": 10,
                              "tag": "9e107d9d372bb6826bd81d3542a419d6",
                              "description": "Camisa 10 da seleção Argentina",
                              "imageUrl": "/stickers/8f14e45f-ceea-167a-5a36-dedd4bea2543/image",
                              "createdAt": "2026-06-30T12:00:00",
                              "albumId": "30eda801-66b8-4902-a0fc-38a6e692702d"
                            }
                            """))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou figurinha já existente (nome, página/número ou imagem duplicados).",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Dados inválidos", value = """
                                    {
                                      "timestamp": "2026-06-30T12:00:00",
                                      "status": 400,
                                      "error": "Dados inválidos.",
                                      "message": "name: O nome não pode ser vazio ou nulo."
                                    }
                                    """),
                            @ExampleObject(name = "Figurinha já existente", value = """
                                    {
                                      "timestamp": "2026-06-30T12:00:00",
                                      "status": 400,
                                      "error": "Recurso já existente.",
                                      "message": "Esta figurinha já existe."
                                    }
                                    """)
                    })
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de Autor."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Álbum não encontrado.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Álbum não encontrado."
                            }
                            """))
            )
    })
    public ResponseEntity<StickerResponseDTO> create(
            @Valid @RequestBody StickerRegisterRequestDTO request
    ) {
        StickerResponseDTO created = createStickerUseCase.execute(request);
        URI location = URI.create("/stickers/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAuthor()")
    @Operation(
            summary = "Atualiza uma figurinha do catálogo.",
            description = "Atualiza os dados de uma figurinha existente. **Acesso restrito ao perfil Autor.** Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Figurinha atualizada com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "id": "8f14e45f-ceea-167a-5a36-dedd4bea2543",
                              "name": "Lionel Messi",
                              "page": 1,
                              "number": 10,
                              "tag": "9e107d9d372bb6826bd81d3542a419d6",
                              "description": "Camisa 10 da seleção Argentina (atualizada)",
                              "imageUrl": "/stickers/8f14e45f-ceea-167a-5a36-dedd4bea2543/image",
                              "createdAt": "2026-06-30T12:00:00",
                              "albumId": "30eda801-66b8-4902-a0fc-38a6e692702d"
                            }
                            """))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou conflito com outra figurinha (página/número ou imagem duplicados).",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Dados inválidos", value = """
                                    {
                                      "timestamp": "2026-06-30T12:00:00",
                                      "status": 400,
                                      "error": "Dados inválidos.",
                                      "message": "name: O nome não pode ser vazio ou nulo."
                                    }
                                    """),
                            @ExampleObject(name = "Conflito com outra figurinha", value = """
                                    {
                                      "timestamp": "2026-06-30T12:00:00",
                                      "status": 400,
                                      "error": "Recurso já existente.",
                                      "message": "Já existe outra figurinha cadastrada nesta página e número."
                                    }
                                    """)
                    })
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de Autor."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Figurinha não encontrada.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Figurinha não encontrada com o ID fornecido: 8f14e45f-ceea-167a-5a36-dedd4bea2543"
                            }
                            """))
            )
    })
    public ResponseEntity<StickerResponseDTO> update(
            @Parameter(description = "ID da figurinha a ser atualizada")
            @PathVariable UUID id,
            @Valid @RequestBody StickerUpdateRequestDTO request
    ) {
        StickerResponseDTO updated = updateStickerUseCase.execute(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAuthor()")
    @Operation(
            summary = "Exclui uma figurinha do catálogo.",
            description = "Remove uma figurinha do catálogo. **Acesso restrito ao perfil Autor.** Bloqueada se algum usuário já possuir a figurinha. Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Figurinha excluída com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Figurinha já adquirida por algum usuário.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 400,
                              "error": "Recurso em uso.",
                              "message": "Não é possível excluir esta figurinha, pois ela já foi adquirida por algum usuário."
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de Autor."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Figurinha não encontrada.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-06-30T12:00:00",
                              "status": 404,
                              "error": "Recurso não encontrado.",
                              "message": "Figurinha não encontrada com o ID fornecido: 8f14e45f-ceea-167a-5a36-dedd4bea2543"
                            }
                            """))
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da figurinha a ser excluída")
            @PathVariable UUID id
    ) {
        deleteStickerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("@securityUtils.isValidAuthor()")
    @Operation(
            summary = "Limpa todo o catálogo de figurinhas.",
            description = "Remove todas as figurinhas do álbum, junto com todas as posses (UserSticker) de todos os colecionadores vinculadas a elas. " +
                    "Ação irreversível. **Acesso restrito ao perfil Autor.** Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Catálogo limpo com sucesso."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de Autor.")
    })
    public ResponseEntity<Void> clearCatalog() {
        clearStickerCatalogUseCase.execute();
        return ResponseEntity.noContent().build();
    }
}
