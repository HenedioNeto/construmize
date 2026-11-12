
package br.com.construmize.construmize.dto.response;

import br.com.construmize.construmize.entity.Etapa;

public record EtapaResponse(
        Long id,
        String nome,
        String descricao,
        Long obraId
) {

    public static EtapaResponse deEntidade(Etapa etapa) {
        return new EtapaResponse(
                etapa.getId(),
                etapa.getNome(),
                etapa.getDescricao(),
                etapa.getObra().getId()
        );
    }
}
