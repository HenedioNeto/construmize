
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.dto.request.CriarEtapaRequest;
import br.com.construmize.construmize.dto.response.EtapaResponse;
import br.com.construmize.construmize.service.EtapaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras/{obraId}/etapas")
public class EtapaController {

    private final EtapaService etapaService;

    public EtapaController(EtapaService etapaService) {
        this.etapaService = etapaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EtapaResponse criar(
            @PathVariable Long obraId,
            @Valid @RequestBody CriarEtapaRequest requisicao
    ) {
        return etapaService.criar(obraId, requisicao);
    }

    @GetMapping
    public List<EtapaResponse> listarPorObra(
            @PathVariable Long obraId
    ) {
        return etapaService.listarPorObra(obraId);
    }

    @DeleteMapping("/{etapaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long obraId, @PathVariable Long etapaId) {
        etapaService.excluir(obraId, etapaId);
    }
}
