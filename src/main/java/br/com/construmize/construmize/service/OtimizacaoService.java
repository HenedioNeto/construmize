
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.otimizacao.modelo.ProblemaLinear;
import br.com.construmize.construmize.otimizacao.modelo.ResultadoOtimizacao;
import br.com.construmize.construmize.otimizacao.montagem.MontadorProblemaOtimizacao;
import br.com.construmize.construmize.otimizacao.solver.ServicoSimplex;

import org.springframework.stereotype.Service;

@Service
public class OtimizacaoService {

    private final MontadorProblemaOtimizacao montador;
    private final ServicoSimplex servicoSimplex;

    public OtimizacaoService(
            MontadorProblemaOtimizacao montador,
            ServicoSimplex servicoSimplex
    ) {
        this.montador = montador;
        this.servicoSimplex = servicoSimplex;
    }

    public ResultadoOtimizacao otimizar(Long etapaId) {

        // 1. Monta o problema matemático com os dados do banco.
        ProblemaLinear problema = montador.montar(etapaId);

        // 2. Executa o algoritmo Simplex.
        return servicoSimplex.resolver(problema);
    }
}
