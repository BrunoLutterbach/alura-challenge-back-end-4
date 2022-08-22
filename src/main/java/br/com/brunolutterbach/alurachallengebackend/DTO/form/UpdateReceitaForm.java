package br.com.brunolutterbach.alurachallengebackend.DTO.form;

import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import br.com.brunolutterbach.alurachallengebackend.repository.ReceitaRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UpdateReceitaForm {

    @NotNull @Size(min = 5)
    private String descricao;
    @NotNull
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate data;

    public Receita update(Long id, ReceitaRepository repository) {
        Receita receita = repository.findById(id).get();
        receita.setDescricao(descricao);
        receita.setValor(valor);
        receita.setData(data);
        return receita;
    }
}
