
package br.com.construmize.construmize.otimizacao;

import br.com.construmize.construmize.otimizacao.modelo.*;
import br.com.construmize.construmize.otimizacao.solver.ServicoSimplex;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServicoSimplexTest {

    private final ServicoSimplex servicoSimplex = new ServicoSimplex();

    @Test
    void deveEncontrarCustoMinimoDaFundacao() {

        // Variáveis de decisão e custos unitários
        List<VariavelDecisao> variaveis = List.of(
                variavel("escavacao_propria", "90"),
                variavel("escavacao_terceirizada", "130"),

                variavel("formas_proprias", "60"),
                variavel("formas_terceirizadas", "85"),

                variavel("armacao_propria", "8"),
                variavel("armacao_terceirizada", "10.5"),

                variavel("concretagem_propria", "480"),
                variavel("concretagem_terceirizada", "517.5")
        );

        // Restrições do problema
        List<RestricaoLinear> restricoes = List.of(

                // Demandas obrigatórias
                restricao(
                        "demanda_escavacao",
                        "Quantidade de escavação",
                        Map.of(
                                "escavacao_propria", bd("1"),
                                "escavacao_terceirizada", bd("1")
                        ),
                        OperadorRestricao.IGUAL,
                        "15"
                ),

                restricao(
                        "demanda_formas",
                        "Quantidade de formas",
                        Map.of(
                                "formas_proprias", bd("1"),
                                "formas_terceirizadas", bd("1")
                        ),
                        OperadorRestricao.IGUAL,
                        "40"
                ),

                restricao(
                        "demanda_armacao",
                        "Quantidade de armação",
                        Map.of(
                                "armacao_propria", bd("1"),
                                "armacao_terceirizada", bd("1")
                        ),
                        OperadorRestricao.IGUAL,
                        "350"
                ),

                restricao(
                        "demanda_concretagem",
                        "Quantidade de concretagem",
                        Map.of(
                                "concretagem_propria", bd("1"),
                                "concretagem_terceirizada", bd("1")
                        ),
                        OperadorRestricao.IGUAL,
                        "8"
                ),

                // Disponibilidade de mão de obra
                restricao(
                        "disponibilidade_mao_obra",
                        "Limite de horas de mão de obra",
                        Map.of(
                                "escavacao_propria", bd("2"),
                                "formas_proprias", bd("1"),
                                "armacao_propria", bd("0.1"),
                                "concretagem_propria", bd("2.5")
                        ),
                        OperadorRestricao.MENOR_OU_IGUAL,
                        "80"
                ),

                // Disponibilidade de equipamentos
                restricao(
                        "disponibilidade_equipamentos",
                        "Limite de horas de equipamentos",
                        Map.of(
                                "escavacao_propria", bd("0.8"),
                                "formas_proprias", bd("0.1"),
                                "armacao_propria", bd("0.02"),
                                "concretagem_propria", bd("1.5")
                        ),
                        OperadorRestricao.MENOR_OU_IGUAL,
                        "12"
                )
        );

        ProblemaLinear problema = new ProblemaLinear(
                variaveis,
                restricoes
        );

        // Executa a otimização
        ResultadoOtimizacao resultado = servicoSimplex.resolver(problema);

        // Verifica o resultado
        assertEquals(StatusOtimizacao.OTIMO, resultado.status());

        assertEquals(
                11240.00,
                resultado.valorObjetivo().doubleValue(),
                0.01
        );

        assertValor(resultado, "escavacao_propria", 1.25);
        assertValor(resultado, "escavacao_terceirizada", 13.75);

        assertValor(resultado, "formas_proprias", 40);
        assertValor(resultado, "formas_terceirizadas", 0);

        assertValor(resultado, "armacao_propria", 350);
        assertValor(resultado, "armacao_terceirizada", 0);

        assertValor(resultado, "concretagem_propria", 0);
        assertValor(resultado, "concretagem_terceirizada", 8);
    }

    private VariavelDecisao variavel(String identificador, String custo) {
        return new VariavelDecisao(
                identificador,
                bd(custo)
        );
    }

    private RestricaoLinear restricao(
            String identificador,
            String descricao,
            Map<String, BigDecimal> coeficientes,
            OperadorRestricao operador,
            String limite
    ) {
        return new RestricaoLinear(
                identificador,
                descricao,
                coeficientes,
                operador,
                bd(limite)
        );
    }

    private BigDecimal bd(String valor) {
        return new BigDecimal(valor);
    }

    private void assertValor(
            ResultadoOtimizacao resultado,
            String identificador,
            double esperado
    ) {
        assertEquals(
                esperado,
                resultado.valoresVariaveis()
                        .get(identificador)
                        .doubleValue(),
                0.01,
                "Valor incorreto para " + identificador
        );
    }
}
