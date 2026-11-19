
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.dto.request.CriarAlternativaRequest;
import br.com.construmize.construmize.dto.response.AlternativaResponse;
import br.com.construmize.construmize.service.AlternativaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos/{servicoId}/alternativas")
public class AlternativaController {

    private final AlternativaService alternativaService;

    public AlternativaController(AlternativaService alternativaService) {
        this.alternativaService = alternativaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlternativaResponse criar(
            @PathVariable Long servicoId,
            @Valid @RequestBody CriarAlternativaRequest requisicao
    ) {
        return alternativaService.criar(servicoId, requisicao);
    }

    @GetMapping
    public List<AlternativaResponse> listar(@PathVariable Long servicoId) {
        return alternativaService.listarPorServico(servicoId);
    }

    @DeleteMapping("/{alternativaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long servicoId,
            @PathVariable Long alternativaId
    ) {
        alternativaService.excluir(servicoId, alternativaId);
    }
}
