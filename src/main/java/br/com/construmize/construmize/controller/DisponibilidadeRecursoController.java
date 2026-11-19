
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.dto.request.CriarDisponibilidadeRecursoRequest;
import br.com.construmize.construmize.dto.response.DisponibilidadeRecursoResponse;
import br.com.construmize.construmize.service.DisponibilidadeRecursoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etapas/{etapaId}/disponibilidades")
public class DisponibilidadeRecursoController {

    private final DisponibilidadeRecursoService disponibilidadeService;

    public DisponibilidadeRecursoController(
            DisponibilidadeRecursoService disponibilidadeService
    ) {
        this.disponibilidadeService = disponibilidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisponibilidadeRecursoResponse criar(
            @PathVariable Long etapaId,
            @Valid @RequestBody CriarDisponibilidadeRecursoRequest requisicao
    ) {
        return disponibilidadeService.criar(etapaId, requisicao);
    }

    @GetMapping
    public List<DisponibilidadeRecursoResponse> listar(
            @PathVariable Long etapaId
    ) {
        return disponibilidadeService.listarPorEtapa(etapaId);
    }

    @DeleteMapping("/{disponibilidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long etapaId,
            @PathVariable Long disponibilidadeId
    ) {
        disponibilidadeService.excluir(etapaId, disponibilidadeId);
    }
}
