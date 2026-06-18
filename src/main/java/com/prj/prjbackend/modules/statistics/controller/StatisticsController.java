package com.prj.prjbackend.modules.statistics.controller;

import com.prj.prjbackend.modules.statistics.dto.HomePageResponseDTO;
import com.prj.prjbackend.modules.statistics.service.GetStatsForHomePageUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Estatísticas", description = "Endpoints para estatísticas")
@RestController
@RequestMapping("/statistics/home")
@RequiredArgsConstructor
public class StatisticsController {
    private final GetStatsForHomePageUseCase getStatsForHomePageUseCase;

    @GetMapping
    @Operation(
            summary = "Lista os dados para a página inicial.",
            description = "Retorna todos os dados da página inicial."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Não foi posssível retornar os dados.")
    })
    public ResponseEntity<HomePageResponseDTO> getStatsForHomePage(){
        return ResponseEntity.ok().body(getStatsForHomePageUseCase.execute());
    }
}
