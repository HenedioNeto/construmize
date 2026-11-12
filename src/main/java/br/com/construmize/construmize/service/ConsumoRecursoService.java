
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.dto.request.CriarConsumoRecursoRequest;
import br.com.construmize.construmize.dto.response.ConsumoRecursoResponse;
import br.com.construmize.construmize.entity.Alternativa;
import br.com.construmize.construmize.entity.ConsumoRecurso;
import br.com.construmize.construmize.entity.Recurso;
import br.com.construmize.construmize.repository.AlternativaRepository;
import br.com.construmize.construmize.repository.ConsumoRecursoRepository;
import br.com.construmize.construmize.repository.RecursoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ConsumoRecursoService {

    private final ConsumoRecursoRepository consumoRepository;
    private final AlternativaRepository alternativaRepository;
    private final RecursoRepository recursoRepository;

    public ConsumoRecursoService(
            ConsumoRecursoRepository consumoRepository,
            AlternativaRepository alternativaRepository,
            RecursoRepository recursoRepository
    ) {
        this.consumoRepository = consumoRepository;
        this.alternativaRepository = alternativaRepository;
        this.recursoRepository = recursoRepository;
    }

    @Transactional
    public ConsumoRecursoResponse criar(
            Long alternativaId,
            CriarConsumoRecursoRequest requisicao
    ) {

        Alternativa alternativa = alternativaRepository.findById(alternativaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alternativa não encontrada."
                ));

        Recurso recurso = recursoRepository.findById(requisicao.recursoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recurso não encontrado."
                ));

        boolean jaExiste = consumoRepository
                .findByAlternativaId(alternativaId)
                .stream()
                .anyMatch(consumo ->
                        consumo.getRecurso().getId().equals(recurso.getId())
                );

        if (jaExiste) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse recurso já está associado à alternativa."
            );
        }

        ConsumoRecurso consumo = new ConsumoRecurso();

        consumo.setAlternativa(alternativa);
        consumo.setRecurso(recurso);
        consumo.setQuantidadePorUnidade(
                requisicao.quantidadePorUnidade()
        );

        ConsumoRecurso salvo = consumoRepository.save(consumo);

        return ConsumoRecursoResponse.deEntidade(salvo);
    }

    @Transactional(readOnly = true)
    public List<ConsumoRecursoResponse> listarPorAlternativa(
            Long alternativaId
    ) {

        if (!alternativaRepository.existsById(alternativaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Alternativa não encontrada."
            );
        }

        return consumoRepository.findByAlternativaId(alternativaId)
                .stream()
                .map(ConsumoRecursoResponse::deEntidade)
                .toList();
    }
}
