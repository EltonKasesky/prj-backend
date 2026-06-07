package com.prj.prjbackend.modules.auth.controller;

import com.prj.prjbackend.modules.auth.dto.LoginRequestDTO;
import com.prj.prjbackend.modules.auth.dto.LoginResponseDTO;
import com.prj.prjbackend.modules.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login", description = "Endpoint para login de usuários")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Autentica um usuário.",
            description = "Autentica um usuário baseado em senha e email.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Senha ou email inválidos.")
    })
    public ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "Corpo de autenticação do usuário")
            @Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok().body(authService.authenticate(request));
    }
}