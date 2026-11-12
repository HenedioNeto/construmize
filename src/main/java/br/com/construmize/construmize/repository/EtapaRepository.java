
package br.com.construmize.construmize.repository;

import br.com.construmize.construmize.entity.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtapaRepository extends JpaRepository<Etapa, Long> {

    List<Etapa> findByObraId(Long obraId);

}
