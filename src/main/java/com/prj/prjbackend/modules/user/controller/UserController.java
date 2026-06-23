package com.prj.prjbackend.modules.user.controller;

import com.prj.prjbackend.modules.user.dto.UserPasswordRequestDTO;
import com.prj.prjbackend.modules.user.dto.UserRegisterRequestDTO;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.user.dto.UserUpdateRequestDTO;
import com.prj.prjbackend.modules.user.service.*;
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
import java.util.UUID;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final DisableAuthenticatedUserUseCase disableAuthenticatedUserUseCase;
    private final DisableUserByIdUseCase disableUserByIdUseCase;
    private final ActiveUserUseCase activeUserUseCase;

    @GetMapping
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Lista todos os usuários de forma paginada.",
            description = "Retorna uma lista paginada de todos os usuários cadastrados no sistema. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado.")
    })
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @Parameter(description = "Número da página (começando em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(getAllUsersUseCase.execute(size, page));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Lista o usuário baseado na busca por id.",
            description = "Retorna um usuário provido por um identificador. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado baseado no id buscado.")
    })
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "Id do usuário desejado")
            @Valid @PathVariable UUID id){
        return ResponseEntity.ok().body(getUserByIdUseCase.execute(id));
    }

    @GetMapping("/me")
    @Operation(
            summary = "Lista o usuário baseado na autenticação.",
            description = "Retorna o usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuário está desativado."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado.")
    })
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(){
        return ResponseEntity.ok().body(getAuthenticatedUserUseCase.execute());
    }

    @PostMapping
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Cria um novo usuário.",
            description = "Cria um novo usuário sem perfis de acesso. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de criação inválidos."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado.")
    })
    public ResponseEntity<Void> createUser(
            @Parameter(description = "Corpo de criação do usuário")
            @Valid @RequestBody UserRegisterRequestDTO request){
        createUserUseCase.execute(request);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PatchMapping("/me")
    @Operation(
            summary = "Atualiza dados do usuário autenticado.",
            description = "Atualiza os dados de email e nome do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário está desativado.")
    })
    public ResponseEntity<Void> updateUser(
            @Parameter(description = "Corpo de atualização do usuário")
            @Valid @RequestBody UserUpdateRequestDTO request){
        updateUserUseCase.execute(request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    @Operation(
            summary = "Atualiza a senha do usuário.",
            description = "Atualiza a senha do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Senha inválida."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário está desativado.")
    })
    public ResponseEntity<Void> updatePassword(
            @Parameter(description = "Corpo de nova senha do usuário")
            @Valid @RequestBody UserPasswordRequestDTO request){
        updatePasswordUseCase.execute(request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Ativa um usuário baseado na busca por id.",
            description = "Ativa novamente um usuário desativado. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário ativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuário já está ativo."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado baseado no id buscado.")
    })
    public ResponseEntity<Void> activeUser(
            @Parameter(description = "Id do usuário desejado")
            @PathVariable UUID id){
        activeUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityUtils.isValidAdmin()")
    @Operation(
            summary = "Desativa o usuário baseado na busca por id.",
            description = "Desativa um usuário ativo baseado na busca por id. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuário já está desativado."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário está desativado.")
    })
    public ResponseEntity<Void> disableUserById(
            @Parameter(description = "Id do usuário desejado")
            @PathVariable UUID id){
        disableUserByIdUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Desativa o usuário autenticado.",
            description = "Desativa o usuário autenticado com soft delete.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuário já está desativado."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário está desativado.")
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> disableUser(){
        disableAuthenticatedUserUseCase.execute();
        return ResponseEntity.noContent().build();
    }
}
