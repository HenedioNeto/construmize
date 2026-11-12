
package br.com.construmize.construmize.dto.response;

import br.com.construmize.construmize.entity.Recurso;

public record RecursoResponse(
        Long id,
        String nome,
        String unidadeMedida
) {

    public static RecursoResponse deEntidade(Recurso recurso) {
        return new RecursoResponse(
                recurso.getId(),
                recurso.getNome(),
                recurso.getUnidadeMedida()
        );
    }
}
