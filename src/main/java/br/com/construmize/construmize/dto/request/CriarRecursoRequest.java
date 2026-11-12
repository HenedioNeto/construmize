
package br.com.construmize.construmize.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarRecursoRequest(

        @NotBlank(message = "O nome do recurso é obrigatório.")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String nome,

        @NotBlank(message = "A unidade de medida é obrigatória.")
        @Size(max = 30, message = "A unidade deve ter no máximo 30 caracteres.")
        String unidadeMedida

) {
}
