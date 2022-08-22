package br.com.brunolutterbach.alurachallengebackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "receitas")
@Data
public class Receita {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String descricao;
    private Double valor;
    private LocalDate data;

    public Receita(String descricao, Double valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Receita() {
    }

}


