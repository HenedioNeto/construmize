
package br.com.construmize.construmize.dto.response;

import br.com.construmize.construmize.entity.ConsumoRecurso;

import java.math.BigDecimal;

public record ConsumoRecursoResponse(
        Long id,
        Long alternativaId,
        Long recursoId,
        String nomeRecurso,
        String unidadeMedida,
        BigDecimal quantidadePorUnidade
) {

    public static ConsumoRecursoResponse deEntidade(
            ConsumoRecurso consumo
    ) {
        return new ConsumoRecursoResponse(
                consumo.getId(),
                consumo.getAlternativa().getId(),
                consumo.getRecurso().getId(),
                consumo.getRecurso().getNome(),
                consumo.getRecurso().getUnidadeMedida(),
                consumo.getQuantidadePorUnidade()
        );
    }
}
