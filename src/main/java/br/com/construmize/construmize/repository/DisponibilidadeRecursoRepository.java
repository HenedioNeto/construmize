
package br.com.construmize.construmize.repository;

import br.com.construmize.construmize.entity.DisponibilidadeRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadeRecursoRepository
        extends JpaRepository<DisponibilidadeRecurso, Long> {

    List<DisponibilidadeRecurso> findByEtapaId(Long etapaId);
}
