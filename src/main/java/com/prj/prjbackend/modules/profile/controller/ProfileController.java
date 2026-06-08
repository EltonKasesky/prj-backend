package com.prj.prjbackend.modules.profile.controller;

import com.prj.prjbackend.modules.profile.dto.ProfileRegisterRequestDTO;
import com.prj.prjbackend.modules.profile.dto.ProfileResponseDTO;
import com.prj.prjbackend.modules.profile.dto.ProfileToUserRequestDTO;
import com.prj.prjbackend.modules.profile.dto.ProfileUpdateRequestDTO;
import com.prj.prjbackend.modules.profile.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Perfis", description = "Endpoints para gerenciamento de perfis")
@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final GetAllProfilesUseCase getAllProfilesUseCase;
    private final GetProfileByIdUseCase getProfileByIdUseCase;
    private final GetProfilesFromUserIdUseCase getProfilesFromUserIdUseCase;
    private final CreateProfileUseCase createProfileUseCase;
    private final AddProfileUserUseCase addProfileUserUseCase;
    private final RemoveProfileUserUseCase removeProfileUserUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final ActiveProfileUseCase activeProfileUseCase;
    private final DisableProfileUseCase disableProfileUseCase;

    @GetMapping
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Lista todos os perfis de forma paginada.",
            description = "Retorna uma lista paginada de todos os perfis cadastrados no sistema. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado.")
    })
    public ResponseEntity<Page<ProfileResponseDTO>> getAllProfiles(
            @Parameter(description = "Número da página (começando em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(getAllProfilesUseCase.execute(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Lista o perfil baseado na busca por id.",
            description = "Retorna um perfil provido por um identificador. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado baseado no id buscado.")
    })
    public ResponseEntity<ProfileResponseDTO> getProfileById(
            @Parameter(description = "Id do perfil desejado")
            @PathVariable UUID id){
        return ResponseEntity.ok().body(getProfileByIdUseCase.execute(id));
    }
    
    @GetMapping("/users/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Lista os perfis do usuário baseado na busca por id.",
            description = "Retorna uma lista de perfis provido por um identificador do usuário. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfis retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado baseado no id buscado.")
    })
    public ResponseEntity<List<ProfileResponseDTO>> getProfilesFromUser(
            @Parameter(description = "Id do usuário desejado")
            @PathVariable UUID userId){
        return ResponseEntity.ok().body(getProfilesFromUserIdUseCase.execute(userId));
    }

    @PostMapping
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Cria um novo perfil.",
            description = "Cria um novo perfil de acesso. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de criação inválidos."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado.")
    })
    public ResponseEntity<Void> createProfile(
            @Parameter(description = "Corpo de criação do perfil")
            @Valid @RequestBody ProfileRegisterRequestDTO request){
        createProfileUseCase.execute(request);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/users/add")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Adiciona um perfil para o usuário.",
            description = "Adiciona um perfil para o usuário baseado pela busca de id e nome.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil adicionado para o usuário com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de solicitação inválidos."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Perfil ou usuário não encontrados baseado nos dados de busca.")
    })
    public ResponseEntity<Void> addProfileToUser(
            @Parameter(description = "Corpo de adição de perfil a usuário")
            @RequestBody @Valid ProfileToUserRequestDTO request){
        addProfileUserUseCase.execute(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/remove")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Remove um perfil do usuário.",
            description = "Remove um perfil do usuário baseado pela busca de id e nome.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil remove do usuário com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de solicitação inválidos."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Perfil ou usuário não encontrados baseado nos dados de busca.")
    })
    public ResponseEntity<Void> removeProfileFromUser(
            @Parameter(description = "Corpo de remoção de perfil a usuário")
            @RequestBody @Valid ProfileToUserRequestDTO request){
        removeProfileUserUseCase.execute(request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Atualiza dados do perfil baseado na busca por id.",
            description = "Atualiza o dado de nome do perfil.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado baseado no id buscado.")
    })
    public ResponseEntity<Void> updateProfile(
            @Parameter(description = "Id do perfil desejado")
            @PathVariable UUID id,

            @Parameter(description = "Corpo de atualização do perfil")
            @Valid @RequestBody ProfileUpdateRequestDTO request){
        updateProfileUseCase.execute(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/active/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Ativa um perfil baseado na busca por id.",
            description = "Ativa novamente um perfil desativado. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil ativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Perfil já está ativo."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado baseado no id buscado.")
    })
    public ResponseEntity<Void> activeProfile(
            @Parameter(description = "Id do perfil desejado")
            @PathVariable UUID id){
        activeProfileUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Desativa o perfil baseado na busca por id.",
            description = "Desativa o perfil com soft delete.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil desativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Perfil já está desativado."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado baseado no id buscado.")
    })
    public ResponseEntity<Void> deleteProfile(
            @Parameter(description = "Id do perfil desejado")
            @PathVariable UUID id){
        disableProfileUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
