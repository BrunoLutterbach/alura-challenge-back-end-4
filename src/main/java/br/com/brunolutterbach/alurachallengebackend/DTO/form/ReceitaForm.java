package br.com.brunolutterbach.alurachallengebackend.DTO.form;

import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ReceitaForm {

    @NotNull
    @Size(min = 5)
    private String descricao;
    @NotNull
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate data;


    public Receita converter(Receita receita) {
        return new Receita(descricao, valor, data);
    }
}
