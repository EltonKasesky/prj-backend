package com.prj.prjbackend.modules.admin.controller;

import com.prj.prjbackend.modules.admin.dto.DashboardTabResponseDTO;
import com.prj.prjbackend.modules.admin.service.GetStatsForDashboardTabUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Administrador", description = "Endpoints para dados da administração")
@RestController
@RequestMapping("/admin")
@PreAuthorize("@securityUtils.isValidAdmin()")
@RequiredArgsConstructor
public class AdminController {
    private final GetStatsForDashboardTabUseCase getStatsForDashboardTabUseCase;

    @GetMapping("/dashboard")
    @Operation(
            summary = "Lista os dados para a página de dashboard.",
            description = "Retorna todos os dados da página de dashboard no painel administrativo. **Acesso restrito a Administradores ativos.**",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente, inválido ou expirado."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui perfil de ADMIN ou está desativado.")
    })
    public ResponseEntity<DashboardTabResponseDTO> getStatsForDashboardTab(){
        return ResponseEntity.ok().body(getStatsForDashboardTabUseCase.execute());
    }
}
