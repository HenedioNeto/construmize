
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.dto.request.CriarConsumoRecursoRequest;
import br.com.construmize.construmize.dto.response.ConsumoRecursoResponse;
import br.com.construmize.construmize.service.ConsumoRecursoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alternativas/{alternativaId}/consumos")
public class ConsumoRecursoController {

    private final ConsumoRecursoService consumoService;

    public ConsumoRecursoController(
            ConsumoRecursoService consumoService
    ) {
        this.consumoService = consumoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsumoRecursoResponse criar(
            @PathVariable Long alternativaId,
            @Valid @RequestBody CriarConsumoRecursoRequest requisicao
    ) {
        return consumoService.criar(alternativaId, requisicao);
    }

    @GetMapping
    public List<ConsumoRecursoResponse> listar(
            @PathVariable Long alternativaId
    ) {
        return consumoService.listarPorAlternativa(alternativaId);
    }
}
