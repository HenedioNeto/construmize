
package br.com.construmize.construmize.dto.response;

import br.com.construmize.construmize.entity.DisponibilidadeRecurso;

import java.math.BigDecimal;

public record DisponibilidadeRecursoResponse(
        Long id,
        Long etapaId,
        Long recursoId,
        String nomeRecurso,
        String unidadeMedida,
        BigDecimal quantidadeDisponivel
) {

    public static DisponibilidadeRecursoResponse deEntidade(
            DisponibilidadeRecurso disponibilidade
    ) {
        return new DisponibilidadeRecursoResponse(
                disponibilidade.getId(),
                disponibilidade.getEtapa().getId(),
                disponibilidade.getRecurso().getId(),
                disponibilidade.getRecurso().getNome(),
                disponibilidade.getRecurso().getUnidadeMedida(),
                disponibilidade.getQuantidadeDisponivel()
        );
    }
}
