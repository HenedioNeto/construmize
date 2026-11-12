
package br.com.construmize.construmize.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CriarConsumoRecursoRequest(

        @NotNull(message = "O ID do recurso é obrigatório.")
        Long recursoId,

        @NotNull(message = "A quantidade consumida é obrigatória.")
        @DecimalMin(value = "0.0", inclusive = false,
                message = "A quantidade consumida deve ser maior que zero.")
        @Digits(integer = 12, fraction = 4,
                message = "A quantidade deve ter no máximo 4 casas decimais.")
        BigDecimal quantidadePorUnidade

) {
}
