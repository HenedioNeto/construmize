
package br.com.construmize.construmize.dto.response;

import br.com.construmize.construmize.entity.Alternativa;

import java.math.BigDecimal;

public record AlternativaResponse(
        Long id,
        String nome,
        BigDecimal custoUnitario,
        Long servicoId
) {

    public static AlternativaResponse deEntidade(Alternativa alternativa) {
        return new AlternativaResponse(
                alternativa.getId(),
                alternativa.getNome(),
                alternativa.getCustoUnitario(),
                alternativa.getServico().getId()
        );
    }
}
