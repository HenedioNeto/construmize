package br.com.construmize.construmize.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "disponibilidades_recursos",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"etapa_id", "recurso_id"}
                )
        }
)
public class DisponibilidadeRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal quantidadeDisponivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etapa_id", nullable = false)
    private Etapa etapa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    public DisponibilidadeRecurso() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(BigDecimal quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
}