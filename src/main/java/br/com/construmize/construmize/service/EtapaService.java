
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.dto.request.CriarEtapaRequest;
import br.com.construmize.construmize.dto.response.EtapaResponse;
import br.com.construmize.construmize.entity.Etapa;
import br.com.construmize.construmize.entity.Obra;
import br.com.construmize.construmize.repository.EtapaRepository;
import br.com.construmize.construmize.repository.ObraRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EtapaService {

    private final EtapaRepository etapaRepository;
    private final ObraRepository obraRepository;

    public EtapaService(
            EtapaRepository etapaRepository,
            ObraRepository obraRepository
    ) {
        this.etapaRepository = etapaRepository;
        this.obraRepository = obraRepository;
    }

    public EtapaResponse criar(Long obraId, CriarEtapaRequest requisicao) {

        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Obra não encontrada"
                ));

        Etapa etapa = new Etapa(
                requisicao.nome(),
                requisicao.descricao(),
                obra
        );

        Etapa etapaSalva = etapaRepository.save(etapa);

        return EtapaResponse.deEntidade(etapaSalva);
    }

    public List<EtapaResponse> listarPorObra(Long obraId) {

        if (!obraRepository.existsById(obraId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Obra não encontrada"
            );
        }

        return etapaRepository.findByObraId(obraId)
                .stream()
                .map(EtapaResponse::deEntidade)
                .toList();
    }
}
