
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.dto.request.CriarAlternativaRequest;
import br.com.construmize.construmize.dto.response.AlternativaResponse;
import br.com.construmize.construmize.entity.Alternativa;
import br.com.construmize.construmize.entity.Servico;
import br.com.construmize.construmize.repository.AlternativaRepository;
import br.com.construmize.construmize.repository.ServicoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlternativaService {

    private final AlternativaRepository alternativaRepository;
    private final ServicoRepository servicoRepository;

    public AlternativaService(
            AlternativaRepository alternativaRepository,
            ServicoRepository servicoRepository
    ) {
        this.alternativaRepository = alternativaRepository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public AlternativaResponse criar(
            Long servicoId,
            CriarAlternativaRequest requisicao
    ) {

        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Serviço não encontrado."
                ));

        Alternativa alternativa = new Alternativa();

        alternativa.setNome(requisicao.nome());
        alternativa.setCustoUnitario(requisicao.custoUnitario());
        alternativa.setServico(servico);

        Alternativa salva = alternativaRepository.save(alternativa);

        return AlternativaResponse.deEntidade(salva);
    }

    @Transactional(readOnly = true)
    public List<AlternativaResponse> listarPorServico(Long servicoId) {

        if (!servicoRepository.existsById(servicoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Serviço não encontrado."
            );
        }

        return alternativaRepository.findByServicoId(servicoId)
                .stream()
                .map(AlternativaResponse::deEntidade)
                .toList();
    }

    @Transactional
    public void excluir(Long servicoId, Long alternativaId) {

        Alternativa alternativa = alternativaRepository.findById(alternativaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alternativa não encontrada."
                ));

        if (!alternativa.getServico().getId().equals(servicoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Alternativa não encontrada neste serviço."
            );
        }

        alternativaRepository.delete(alternativa);
    }
}
