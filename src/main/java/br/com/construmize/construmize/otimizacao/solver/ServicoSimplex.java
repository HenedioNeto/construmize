
package br.com.construmize.construmize.otimizacao.solver;

import br.com.construmize.construmize.otimizacao.modelo.*;

import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.apache.commons.math3.optim.linear.Relationship.*;

import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.springframework.stereotype.Service;

@Service
public class ServicoSimplex {

    private static final int MAX_ITERACOES = 1000;
    private static final double TOLERANCIA_ZERO = 1e-8;
    private static final int CASAS_DECIMAIS = 6;

    public ResultadoOtimizacao resolver(ProblemaLinear problema) {

        validarProblema(problema);

        try {
            List<VariavelDecisao> variaveis = problema.variaveis();

            // 1. Monta os coeficientes da função objetivo.
            double[] coeficientesObjetivo = variaveis.stream()
                    .mapToDouble(v -> v.coeficienteObjetivo().doubleValue())
                    .toArray();

            LinearObjectiveFunction funcaoObjetivo =
                    new LinearObjectiveFunction(coeficientesObjetivo, 0);

            // 2. Converte as restrições para o formato da biblioteca.
            List<LinearConstraint> restricoes = new ArrayList<>();

            for (RestricaoLinear restricao : problema.restricoes()) {

                double[] coeficientes = new double[variaveis.size()];

                for (int i = 0; i < variaveis.size(); i++) {
                    String identificador = variaveis.get(i).identificador();

                    BigDecimal coeficiente = restricao.coeficientes()
                            .getOrDefault(identificador, BigDecimal.ZERO);

                    coeficientes[i] = coeficiente.doubleValue();
                }

                Relationship operador = converterOperador(restricao.operador());

                restricoes.add(new LinearConstraint(
                        coeficientes,
                        operador,
                        restricao.limite().doubleValue()
                ));
            }

            // 3. Executa o Simplex para minimizar a função objetivo.
            SimplexSolver solucionador = new SimplexSolver();

            PointValuePair solucao = solucionador.optimize(
                    new MaxIter(MAX_ITERACOES),
                    funcaoObjetivo,
                    new LinearConstraintSet(restricoes),
                    GoalType.MINIMIZE,
                    new NonNegativeConstraint(true),
                    PivotSelectionRule.BLAND
            );

            // 4. Converte os resultados para nossas classes.
            return converterResultado(solucao, variaveis);

        } catch (NoFeasibleSolutionException e) {

            return resultadoSemSolucao(
                    StatusOtimizacao.INVIAVEL,
                    "Não existe solução que satisfaça todas as restrições."
            );

        } catch (UnboundedSolutionException e) {

            return resultadoSemSolucao(
                    StatusOtimizacao.ILIMITADO,
                    "O problema possui uma função objetivo ilimitada."
            );

        } catch (TooManyIterationsException e) {

            return resultadoSemSolucao(
                    StatusOtimizacao.LIMITE_ITERACOES,
                    "O limite máximo de iterações foi atingido."
            );
        }
    }

    private Relationship converterOperador(OperadorRestricao operador) {

        return switch (operador) {
            case MENOR_OU_IGUAL -> LEQ;
            case MAIOR_OU_IGUAL -> GEQ;
            case IGUAL -> EQ;
        };
    }

    private ResultadoOtimizacao converterResultado(
            PointValuePair solucao,
            List<VariavelDecisao> variaveis
    ) {

        double[] valores = solucao.getPoint();

        Map<String, BigDecimal> valoresVariaveis = new LinkedHashMap<>();

        for (int i = 0; i < variaveis.size(); i++) {

            double valor = valores[i];

            if (Math.abs(valor) < TOLERANCIA_ZERO) {
                valor = 0;
            }

            valoresVariaveis.put(
                    variaveis.get(i).identificador(),
                    BigDecimal.valueOf(valor)
                            .setScale(CASAS_DECIMAIS, RoundingMode.HALF_UP)
            );
        }

        BigDecimal valorObjetivo = BigDecimal.valueOf(solucao.getValue())
                .setScale(CASAS_DECIMAIS, RoundingMode.HALF_UP);

        return new ResultadoOtimizacao(
                StatusOtimizacao.OTIMO,
                valorObjetivo,
                valoresVariaveis,
                "Otimização realizada com sucesso."
        );
    }

    private ResultadoOtimizacao resultadoSemSolucao(
            StatusOtimizacao status,
            String mensagem
    ) {

        return new ResultadoOtimizacao(
                status,
                null,
                Map.of(),
                mensagem
        );
    }

    private void validarProblema(ProblemaLinear problema) {

        Objects.requireNonNull(problema, "O problema é obrigatório.");

        Set<String> identificadores = new HashSet<>();

        for (VariavelDecisao variavel : problema.variaveis()) {

            if (!identificadores.add(variavel.identificador())) {
                throw new IllegalArgumentException(
                        "Identificador de variável duplicado: " + variavel.identificador()
                );
            }
        }

        for (RestricaoLinear restricao : problema.restricoes()) {

            for (String identificador : restricao.coeficientes().keySet()) {

                if (!identificadores.contains(identificador)) {
                    throw new IllegalArgumentException(
                            "A restrição '" + restricao.identificador()
                                    + "' utiliza uma variável inexistente: " + identificador
                    );
                }
            }
        }
    }
}
