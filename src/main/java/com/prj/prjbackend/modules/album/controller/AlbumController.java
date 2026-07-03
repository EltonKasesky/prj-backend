package com.prj.prjbackend.modules.album.controller;

import com.prj.prjbackend.modules.album.dto.AlbumDetailsDTO;
import com.prj.prjbackend.modules.album.dto.AlbumImageDTO;
import com.prj.prjbackend.modules.album.dto.AlbumUpdateRequestDTO;
import com.prj.prjbackend.modules.album.service.GetAlbumCoverImageUseCase;
import com.prj.prjbackend.modules.album.service.GetAlbumService;
import com.prj.prjbackend.modules.album.service.UpdateAlbumUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Álbum", description = "Endpoint para consulta e gerenciamento do álbum oficial")
@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    private final GetAlbumService getAlbumService;
    private final GetAlbumCoverImageUseCase getAlbumCoverImageUseCase;
    private final UpdateAlbumUseCase updateAlbumUseCase;

    @GetMapping
    @Operation(
            summary = "Obtém os dados do álbum oficial.",
            description = "Retorna o único álbum existente na aplicação, junto com suas figurinhas. Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Álbum obtido com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "id": "30eda801-66b8-4902-a0fc-38a6e692702d",
                              "title": "Copa do Mundo FIFA 2026",
                              "coverImageUrl": "/album/cover-image",
                              "totalPages": 112,
                              "totalStickers": 980,
                              "stickers": [
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
                              ]
                            }
                            """))
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou ausente."),
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
    public ResponseEntity<AlbumDetailsDTO> get() {
        AlbumDetailsDTO album = getAlbumService.execute();
        return ResponseEntity.ok(album);
    }

    @GetMapping("/cover-image")
    @Operation(
            summary = "Obtém a imagem de capa do álbum.",
            description = "Retorna os bytes da imagem de capa do álbum oficial. Requer autenticação JWT.",
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
    public ResponseEntity<byte[]> getCoverImage() {
        AlbumImageDTO image = getAlbumCoverImageUseCase.execute();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.imageType()))
                .body(image.image());
    }

    @PutMapping
    @PreAuthorize("@securityUtils.isValidAuthor()")
    @Operation(
            summary = "Atualiza os dados do álbum oficial.",
            description = "Atualiza título, total de páginas, total de figurinhas e, opcionalmente, a imagem de capa do álbum. " +
                    "**Acesso restrito ao perfil Autor.** Requer autenticação JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Álbum atualizado com sucesso.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "id": "30eda801-66b8-4902-a0fc-38a6e692702d",
                              "title": "Copa do Mundo FIFA 2026",
                              "coverImageUrl": "/album/cover-image",
                              "totalPages": 112,
                              "totalStickers": 980,
                              "stickers": []
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
                              "message": "title: O título não pode ser vazio ou nulo."
                            }
                            """))
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
    public ResponseEntity<AlbumDetailsDTO> update(
            @Valid @RequestBody AlbumUpdateRequestDTO request
    ) {
        AlbumDetailsDTO updated = updateAlbumUseCase.execute(request);
        return ResponseEntity.ok(updated);
    }
}
