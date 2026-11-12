
package br.com.construmize.construmize.repository;

import br.com.construmize.construmize.entity.Restricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestricaoRepository
        extends JpaRepository<Restricao, Long> {

    List<Restricao> findByEtapaId(Long etapaId);
}
