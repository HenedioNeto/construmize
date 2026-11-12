
package br.com.construmize.construmize.otimizacao.modelo;

import java.math.BigDecimal;
import java.util.Objects;

public record VariavelDecisao(
        String identificador,
        BigDecimal coeficienteObjetivo
) {

    public VariavelDecisao {
        Objects.requireNonNull(identificador, "O identificador é obrigatório.");
        Objects.requireNonNull(coeficienteObjetivo, "O coeficiente objetivo é obrigatório.");

        if (identificador.isBlank()) {
            throw new IllegalArgumentException("O identificador não pode estar vazio.");
        }
    }
}
