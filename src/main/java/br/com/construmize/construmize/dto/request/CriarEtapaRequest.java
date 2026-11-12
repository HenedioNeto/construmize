
package br.com.construmize.construmize.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarEtapaRequest(

        @NotBlank(message = "O nome da etapa é obrigatório")
        @Size(max = 150)
        String nome,

        @Size(max = 500)
        String descricao

) {
}
