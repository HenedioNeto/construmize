
package br.com.construmize.construmize.dto.response;

import br.com.construmize.construmize.entity.Obra;
import java.time.LocalDateTime;

public record ObraResponse(
        Long id,
        String nome,
        String descricao,
        LocalDateTime dataCriacao
) {

    public static ObraResponse deEntidade(Obra obra) {
        return new ObraResponse(
                obra.getId(),
                obra.getNome(),
                obra.getDescricao(),
                obra.getDataCriacao()
        );
    }
}
