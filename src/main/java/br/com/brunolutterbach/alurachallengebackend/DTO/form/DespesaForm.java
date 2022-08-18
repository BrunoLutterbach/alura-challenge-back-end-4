package br.com.brunolutterbach.alurachallengebackend.DTO.form;

import br.com.brunolutterbach.alurachallengebackend.enums.Categoria;
import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class DespesaForm {

    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private String categoria;

    public Despesa converter(Despesa despesa) {
        despesa.setDescricao(descricao);
        despesa.setValor(valor);
        despesa.setData(data);
        if (categoria != null) {
            despesa.setCategoria(Categoria.valueOf(categoria));
        } else {
            despesa.setCategoria(Categoria.OUTROS);
        }
        return despesa;
    }


}
