
package br.com.construmize.construmize.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CriarServicoRequest(

        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(max = 150)
        String nome,

        @NotNull(message = "A quantidade é obrigatória")
        @DecimalMin(value = "0.0", inclusive = false,
                message = "A quantidade deve ser maior que zero")
        @Digits(integer = 12, fraction = 3)
        BigDecimal quantidade,

        @NotBlank(message = "A unidade de medida é obrigatória")
        @Size(max = 20)
        String unidadeMedida

) {
}
