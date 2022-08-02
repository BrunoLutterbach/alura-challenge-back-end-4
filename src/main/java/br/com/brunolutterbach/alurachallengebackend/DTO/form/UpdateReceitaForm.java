package br.com.brunolutterbach.alurachallengebackend.DTO.form;

import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import br.com.brunolutterbach.alurachallengebackend.repository.ReceitaRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateReceitaForm {

    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    public Receita update(Long id, ReceitaRepository repository) {
        Receita receita = repository.findById(id).get();
        receita.setDescricao(descricao);
        receita.setValor(valor);
        receita.setData(data);
        return receita;
    }
}
