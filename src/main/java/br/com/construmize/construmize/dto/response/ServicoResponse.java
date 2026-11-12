
package br.com.construmize.construmize.dto.response;

import br.com.construmize.construmize.entity.Servico;
import java.math.BigDecimal;

public record ServicoResponse(
        Long id,
        String nome,
        BigDecimal quantidade,
        String unidadeMedida,
        Long etapaId
) {

    public static ServicoResponse deEntidade(Servico servico) {
        return new ServicoResponse(
                servico.getId(),
                servico.getNome(),
                servico.getQuantidade(),
                servico.getUnidadeMedida(),
                servico.getEtapa().getId()
        );
    }
}
