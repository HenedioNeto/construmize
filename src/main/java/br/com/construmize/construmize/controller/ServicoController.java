
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.dto.request.CriarServicoRequest;
import br.com.construmize.construmize.dto.response.ServicoResponse;
import br.com.construmize.construmize.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/etapas/{etapaId}/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoResponse criar(
            @PathVariable Long etapaId,
            @Valid @RequestBody CriarServicoRequest requisicao
    ) {
        return servicoService.criar(etapaId, requisicao);
    }

    @GetMapping
    public List<ServicoResponse> listarPorEtapa(
            @PathVariable Long etapaId
    ) {
        return servicoService.listarPorEtapa(etapaId);
    }


    @PostMapping("/lote")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ServicoResponse> criarEmLote(
            @PathVariable Long etapaId,
            @Valid @RequestBody
            List<@Valid CriarServicoRequest> requisicoes
    ) {
        return servicoService.criarEmLote(etapaId, requisicoes);
    }

}
