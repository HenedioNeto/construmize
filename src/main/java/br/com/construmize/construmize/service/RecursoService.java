
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.dto.request.CriarRecursoRequest;
import br.com.construmize.construmize.dto.response.RecursoResponse;
import br.com.construmize.construmize.entity.Recurso;
import br.com.construmize.construmize.repository.RecursoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Transactional
    public RecursoResponse criar(CriarRecursoRequest requisicao) {

        Recurso recurso = new Recurso();

        recurso.setNome(requisicao.nome());
        recurso.setUnidadeMedida(requisicao.unidadeMedida());

        Recurso salvo = recursoRepository.save(recurso);

        return RecursoResponse.deEntidade(salvo);
    }

    @Transactional(readOnly = true)
    public List<RecursoResponse> listarTodos() {

        return recursoRepository.findAll()
                .stream()
                .map(RecursoResponse::deEntidade)
                .toList();
    }
}
