package com.example.proyect.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String enunciado;
    
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pregunta", orphanRemoval = true)
    private List<Opcion> opciones;
    
    private boolean esMultiple;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public boolean isEsMultiple() {
        return esMultiple;
    }

    public void setEsMultiple(boolean esMultiple) {
        this.esMultiple = esMultiple;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
