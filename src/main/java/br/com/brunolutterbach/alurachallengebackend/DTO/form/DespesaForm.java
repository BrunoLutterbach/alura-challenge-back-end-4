package br.com.brunolutterbach.alurachallengebackend.DTO.form;

import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DespesaForm {

    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    public Despesa converter(Despesa despesa) {
        return new Despesa(descricao, valor, data);
    }


}
