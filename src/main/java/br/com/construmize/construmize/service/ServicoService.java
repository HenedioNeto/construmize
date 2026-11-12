
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.dto.request.CriarServicoRequest;
import br.com.construmize.construmize.dto.response.ServicoResponse;
import br.com.construmize.construmize.entity.Etapa;
import br.com.construmize.construmize.entity.Servico;
import br.com.construmize.construmize.repository.EtapaRepository;
import br.com.construmize.construmize.repository.ServicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final EtapaRepository etapaRepository;

    public ServicoService(
            ServicoRepository servicoRepository,
            EtapaRepository etapaRepository
    ) {
        this.servicoRepository = servicoRepository;
        this.etapaRepository = etapaRepository;
    }

    @Transactional
    public ServicoResponse criar(
            Long etapaId,
            CriarServicoRequest requisicao
    ) {

        Etapa etapa = etapaRepository.findById(etapaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Etapa não encontrada"
                ));

        Servico servico = new Servico(
                requisicao.nome(),
                requisicao.quantidade(),
                requisicao.unidadeMedida(),
                etapa
        );

        Servico servicoSalvo = servicoRepository.save(servico);

        return ServicoResponse.deEntidade(servicoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ServicoResponse> listarPorEtapa(Long etapaId) {

        if (!etapaRepository.existsById(etapaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Etapa não encontrada"
            );
        }

        return servicoRepository.findByEtapaId(etapaId)
                .stream()
                .map(ServicoResponse::deEntidade)
                .toList();
    }


    @Transactional
    public List<ServicoResponse> criarEmLote(
            Long etapaId,
            List<CriarServicoRequest> requisicoes
    ) {

        if (requisicoes == null || requisicoes.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A lista de serviços não pode estar vazia"
            );
        }

        Etapa etapa = etapaRepository.findById(etapaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Etapa não encontrada"
                ));

        List<Servico> servicos = requisicoes.stream()
                .map(requisicao -> new Servico(
                        requisicao.nome(),
                        requisicao.quantidade(),
                        requisicao.unidadeMedida(),
                        etapa
                ))
                .toList();

        List<Servico> servicosSalvos =
                servicoRepository.saveAll(servicos);

        return servicosSalvos.stream()
                .map(ServicoResponse::deEntidade)
                .toList();
    }

}
