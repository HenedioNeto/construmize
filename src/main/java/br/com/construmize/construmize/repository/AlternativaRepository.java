
package br.com.construmize.construmize.repository;

import br.com.construmize.construmize.entity.Alternativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlternativaRepository
        extends JpaRepository<Alternativa, Long> {

    List<Alternativa> findByServicoId(Long servicoId);

    List<Alternativa> findByServicoEtapaId(Long etapaId);
}
