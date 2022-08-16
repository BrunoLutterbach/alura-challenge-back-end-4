package br.com.brunolutterbach.alurachallengebackend.model;

import br.com.brunolutterbach.alurachallengebackend.enums.Categoria;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "despesas")
@Data
public class Despesa {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Despesa(String descricao, Double valor, LocalDate data, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public Despesa() {

    }

}

