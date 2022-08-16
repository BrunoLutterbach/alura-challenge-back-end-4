package br.com.brunolutterbach.alurachallengebackend.DTO.form;

import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReceitaForm {

    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;


    public Receita converter(Receita receita) {
        return new Receita(descricao, valor, data);
    }
}
