
package br.com.construmize.construmize.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record CriarAlternativaRequest(

        @NotBlank(message = "O nome da alternativa é obrigatório.")
        String nome,

        @NotNull(message = "O custo unitário é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "O custo unitário não pode ser negativo.")
        @Digits(integer = 12, fraction = 2,
                message = "O custo unitário deve ter no máximo 2 casas decimais.")
        BigDecimal custoUnitario

) {
}
