
package br.com.construmize.construmize.service;

import br.com.construmize.construmize.dto.request.CriarObraRequest;
import br.com.construmize.construmize.dto.response.ObraResponse;
import br.com.construmize.construmize.entity.Obra;
import br.com.construmize.construmize.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    public ObraResponse criar(CriarObraRequest requisicao) {

        Obra obra = new Obra(
                requisicao.nome(),
                requisicao.descricao()
        );

        Obra obraSalva = obraRepository.save(obra);

        return ObraResponse.deEntidade(obraSalva);
    }

    public List<ObraResponse> listarTodas() {
        return obraRepository.findAll()
                .stream()
                .map(ObraResponse::deEntidade)
                .toList();
    }

    public void excluir(Long id) {

        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        obraRepository.delete(obra);
    }
}
