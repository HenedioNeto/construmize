
package br.com.construmize.construmize.otimizacao.modelo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public record ResultadoOtimizacao(
        StatusOtimizacao status,
        BigDecimal valorObjetivo,
        Map<String, BigDecimal> valoresVariaveis,
        String mensagem
) {

    public ResultadoOtimizacao {
        Objects.requireNonNull(status, "O status é obrigatório.");
        Objects.requireNonNull(valoresVariaveis, "Os valores das variáveis são obrigatórios.");
        Objects.requireNonNull(mensagem, "A mensagem é obrigatória.");

        valoresVariaveis = Map.copyOf(valoresVariaveis);
    }
}
