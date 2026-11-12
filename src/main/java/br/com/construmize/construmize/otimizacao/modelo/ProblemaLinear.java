
package br.com.construmize.construmize.otimizacao.modelo;

import java.util.List;
import java.util.Objects;

public record ProblemaLinear(
        List<VariavelDecisao> variaveis,
        List<RestricaoLinear> restricoes
) {

    public ProblemaLinear {
        Objects.requireNonNull(variaveis, "As variáveis são obrigatórias.");
        Objects.requireNonNull(restricoes, "As restrições são obrigatórias.");

        if (variaveis.isEmpty()) {
            throw new IllegalArgumentException("O problema precisa ter pelo menos uma variável.");
        }

        variaveis = List.copyOf(variaveis);
        restricoes = List.copyOf(restricoes);
    }
}
