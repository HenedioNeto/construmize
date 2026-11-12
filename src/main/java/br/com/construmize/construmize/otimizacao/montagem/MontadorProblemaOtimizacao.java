
package br.com.construmize.construmize.otimizacao.montagem;

import br.com.construmize.construmize.entity.*;
import br.com.construmize.construmize.otimizacao.modelo.*;
import br.com.construmize.construmize.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MontadorProblemaOtimizacao {

    private final ServicoRepository servicoRepository;
    private final AlternativaRepository alternativaRepository;
    private final ConsumoRecursoRepository consumoRepository;
    private final DisponibilidadeRecursoRepository disponibilidadeRepository;

    public MontadorProblemaOtimizacao(
            ServicoRepository servicoRepository,
            AlternativaRepository alternativaRepository,
            ConsumoRecursoRepository consumoRepository,
            DisponibilidadeRecursoRepository disponibilidadeRepository
    ) {
        this.servicoRepository = servicoRepository;
        this.alternativaRepository = alternativaRepository;
        this.consumoRepository = consumoRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    @Transactional(readOnly = true)
    public ProblemaLinear montar(Long etapaId) {

        // 1. Busca os serviços da etapa.
        List<Servico> servicos = servicoRepository.findByEtapaId(etapaId);

        if (servicos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A etapa não possui serviços cadastrados."
            );
        }

        // 2. Busca todas as alternativas dos serviços da etapa.
        List<Alternativa> alternativas =
                alternativaRepository.findByServicoEtapaId(etapaId);

        if (alternativas.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A etapa não possui alternativas cadastradas."
            );
        }

        // 3. Agrupa as alternativas por serviço.
        Map<Long, List<Alternativa>> alternativasPorServico =
                alternativas.stream()
                        .collect(Collectors.groupingBy(
                                alternativa -> alternativa.getServico().getId()
                        ));

        // 4. Cria uma variável de decisão para cada alternativa.
        List<VariavelDecisao> variaveis = alternativas.stream()
                .map(alternativa -> new VariavelDecisao(
                        identificadorAlternativa(alternativa),
                        alternativa.getCustoUnitario()
                ))
                .toList();

        // 5. Busca os consumos de recursos de todas as alternativas.
        List<Long> alternativaIds = alternativas.stream()
                .map(Alternativa::getId)
                .toList();

        List<ConsumoRecurso> consumos =
                consumoRepository.findByAlternativaIdIn(alternativaIds);

        Map<Long, List<ConsumoRecurso>> consumosPorAlternativa =
                consumos.stream()
                        .collect(Collectors.groupingBy(
                                consumo -> consumo.getAlternativa().getId()
                        ));

        // 6. Cria as restrições de demanda dos serviços.
        List<RestricaoLinear> restricoes = new ArrayList<>();

        for (Servico servico : servicos) {

            List<Alternativa> alternativasServico =
                    alternativasPorServico.get(servico.getId());

            if (alternativasServico == null || alternativasServico.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O serviço '" + servico.getNome()
                                + "' não possui alternativas cadastradas."
                );
            }

            Map<String, java.math.BigDecimal> coeficientes =
                    new LinkedHashMap<>();

            for (Alternativa alternativa : alternativasServico) {
                coeficientes.put(
                        identificadorAlternativa(alternativa),
                        java.math.BigDecimal.ONE
                );
            }

            restricoes.add(new RestricaoLinear(
                    "demanda_servico_" + servico.getId(),
                    "Quantidade necessária de " + servico.getNome(),
                    coeficientes,
                    OperadorRestricao.IGUAL,
                    servico.getQuantidade()
            ));
        }

        // 7. Busca as disponibilidades de recursos da etapa.
        List<DisponibilidadeRecurso> disponibilidades =
                disponibilidadeRepository.findByEtapaId(etapaId);

        if (disponibilidades.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A etapa não possui disponibilidades de recursos cadastradas."
            );
        }

        // 8. Cria uma restrição para cada recurso disponível.
        for (DisponibilidadeRecurso disponibilidade : disponibilidades) {

            Long recursoId = disponibilidade.getRecurso().getId();

            Map<String, java.math.BigDecimal> coeficientes =
                    new LinkedHashMap<>();

            for (Alternativa alternativa : alternativas) {

                List<ConsumoRecurso> consumosAlternativa =
                        consumosPorAlternativa.getOrDefault(
                                alternativa.getId(),
                                Collections.emptyList()
                        );

                consumosAlternativa.stream()
                        .filter(consumo ->
                                consumo.getRecurso().getId().equals(recursoId)
                        )
                        .findFirst()
                        .ifPresent(consumo ->
                                coeficientes.put(
                                        identificadorAlternativa(alternativa),
                                        consumo.getQuantidadePorUnidade()
                                )
                        );
            }

            restricoes.add(new RestricaoLinear(
                    "disponibilidade_recurso_" + recursoId,
                    "Disponibilidade de " + disponibilidade.getRecurso().getNome(),
                    coeficientes,
                    OperadorRestricao.MENOR_OU_IGUAL,
                    disponibilidade.getQuantidadeDisponivel()
            ));
        }

        // 9. Retorna o problema matemático completo.
        return new ProblemaLinear(variaveis, restricoes);
    }

    private String identificadorAlternativa(Alternativa alternativa) {
        return "alternativa_" + alternativa.getId();
    }
}
