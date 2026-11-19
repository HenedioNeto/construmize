
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.dto.request.CriarDisponibilidadeRecursoRequest;
import br.com.construmize.construmize.dto.response.DisponibilidadeRecursoResponse;
import br.com.construmize.construmize.entity.DisponibilidadeRecurso;
import br.com.construmize.construmize.entity.Etapa;
import br.com.construmize.construmize.entity.Recurso;
import br.com.construmize.construmize.repository.DisponibilidadeRecursoRepository;
import br.com.construmize.construmize.repository.EtapaRepository;
import br.com.construmize.construmize.repository.RecursoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DisponibilidadeRecursoService {

    private final DisponibilidadeRecursoRepository disponibilidadeRepository;
    private final EtapaRepository etapaRepository;
    private final RecursoRepository recursoRepository;

    public DisponibilidadeRecursoService(
            DisponibilidadeRecursoRepository disponibilidadeRepository,
            EtapaRepository etapaRepository,
            RecursoRepository recursoRepository
    ) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.etapaRepository = etapaRepository;
        this.recursoRepository = recursoRepository;
    }

    @Transactional
    public DisponibilidadeRecursoResponse criar(
            Long etapaId,
            CriarDisponibilidadeRecursoRequest requisicao
    ) {

        Etapa etapa = etapaRepository.findById(etapaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Etapa não encontrada."
                ));

        Recurso recurso = recursoRepository.findById(requisicao.recursoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recurso não encontrado."
                ));

        boolean jaExiste = disponibilidadeRepository
                .findByEtapaId(etapaId)
                .stream()
                .anyMatch(disponibilidade ->
                        disponibilidade.getRecurso().getId().equals(recurso.getId())
                );

        if (jaExiste) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse recurso já possui disponibilidade cadastrada nesta etapa."
            );
        }

        DisponibilidadeRecurso disponibilidade = new DisponibilidadeRecurso();

        disponibilidade.setEtapa(etapa);
        disponibilidade.setRecurso(recurso);
        disponibilidade.setQuantidadeDisponivel(
                requisicao.quantidadeDisponivel()
        );

        DisponibilidadeRecurso salva =
                disponibilidadeRepository.save(disponibilidade);

        return DisponibilidadeRecursoResponse.deEntidade(salva);
    }

    @Transactional(readOnly = true)
    public List<DisponibilidadeRecursoResponse> listarPorEtapa(Long etapaId) {

        if (!etapaRepository.existsById(etapaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Etapa não encontrada."
            );
        }

        return disponibilidadeRepository.findByEtapaId(etapaId)
                .stream()
                .map(DisponibilidadeRecursoResponse::deEntidade)
                .toList();
    }

    @Transactional
    public void excluir(Long etapaId, Long disponibilidadeId) {

        DisponibilidadeRecurso disponibilidade =
                disponibilidadeRepository.findById(disponibilidadeId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Disponibilidade de recurso não encontrada."
                        ));

        if (!disponibilidade.getEtapa().getId().equals(etapaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Disponibilidade não encontrada nesta etapa."
            );
        }

        disponibilidadeRepository.delete(disponibilidade);
    }
}
