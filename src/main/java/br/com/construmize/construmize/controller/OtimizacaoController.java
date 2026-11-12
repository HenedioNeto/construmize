
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.otimizacao.modelo.ResultadoOtimizacao;
import br.com.construmize.construmize.service.OtimizacaoService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/etapas/{etapaId}/otimizacoes")
public class OtimizacaoController {

    private final OtimizacaoService otimizacaoService;

    public OtimizacaoController(OtimizacaoService otimizacaoService) {
        this.otimizacaoService = otimizacaoService;
    }

    @PostMapping
    public ResultadoOtimizacao otimizar(@PathVariable Long etapaId) {
        ResultadoOtimizacao resultado = otimizacaoService.otimizar(etapaId);

        return resultado;
    }
}
