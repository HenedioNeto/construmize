
package br.com.construmize.construmize.repository;

import br.com.construmize.construmize.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByEtapaId(Long etapaId);

}
