
package br.com.construmize.construmize.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "consumos_recursos",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"alternativa_id", "recurso_id"}
                )
        }
)
public class ConsumoRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "quantidade_por_unidade",
            nullable = false,
            precision = 12,
            scale = 4
    )
    private BigDecimal quantidadePorUnidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alternativa_id", nullable = false)
    private Alternativa alternativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    public ConsumoRecurso() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getQuantidadePorUnidade() {
        return quantidadePorUnidade;
    }

    public void setQuantidadePorUnidade(BigDecimal quantidadePorUnidade) {
        this.quantidadePorUnidade = quantidadePorUnidade;
    }

    public Alternativa getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
}
