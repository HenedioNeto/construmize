
package br.com.construmize.construmize.repository;

import br.com.construmize.construmize.entity.ConsumoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ConsumoRecursoRepository
        extends JpaRepository<ConsumoRecurso, Long> {

    List<ConsumoRecurso> findByAlternativaId(Long alternativaId);

    List<ConsumoRecurso> findByAlternativaIdIn(
            Collection<Long> alternativaIds
    );
}
