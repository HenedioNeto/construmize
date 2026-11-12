
package br.com.construmize.construmize.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "etapas")
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    public Etapa() {
    }

    public Etapa(String nome, String descricao, Obra obra) {
        this.nome = nome;
        this.descricao = descricao;
        this.obra = obra;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Obra getObra() {
        return obra;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }
}
