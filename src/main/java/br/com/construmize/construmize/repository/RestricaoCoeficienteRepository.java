
package br.com.construmize.construmize.repository;

import br.com.construmize.construmize.entity.RestricaoCoeficiente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RestricaoCoeficienteRepository
        extends JpaRepository<RestricaoCoeficiente, Long> {

    List<RestricaoCoeficiente> findByRestricaoId(Long restricaoId);

    List<RestricaoCoeficiente> findByRestricaoIdIn(
            Collection<Long> restricaoIds
    );
}
