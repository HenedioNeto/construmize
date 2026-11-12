
package br.com.construmize.construmize.otimizacao.modelo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public record RestricaoLinear(
        String identificador,
        String descricao,
        Map<String, BigDecimal> coeficientes,
        OperadorRestricao operador,
        BigDecimal limite
) {

    public RestricaoLinear {
        Objects.requireNonNull(identificador, "O identificador é obrigatório.");
        Objects.requireNonNull(descricao, "A descrição é obrigatória.");
        Objects.requireNonNull(coeficientes, "Os coeficientes são obrigatórios.");
        Objects.requireNonNull(operador, "O operador é obrigatório.");
        Objects.requireNonNull(limite, "O limite é obrigatório.");

        if (identificador.isBlank()) {
            throw new IllegalArgumentException("O identificador não pode estar vazio.");
        }

        coeficientes = Map.copyOf(coeficientes);
    }
}
