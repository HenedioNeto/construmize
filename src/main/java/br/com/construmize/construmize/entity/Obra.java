package br.com.construmize.construmize.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "obras")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(
            mappedBy = "obra",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Etapa> etapas = new ArrayList<>();

    @PrePersist
    protected void aoCriar() {
        dataCriacao = LocalDateTime.now();
    }

    public Obra() {
    }

    public Obra(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}