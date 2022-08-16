package br.com.brunolutterbach.alurachallengebackend.DTO;

import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ReceitaDTO {

    private Long id;
    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;


    public ReceitaDTO(Receita receita) {
        this.id = receita.getId();
        this.descricao = receita.getDescricao();
        this.valor = receita.getValor();
        this.data = receita.getData();
    }

    public ReceitaDTO() {
    }

    public static List<ReceitaDTO> converter(List<Receita> receitas) {
        return receitas.stream().map(ReceitaDTO::new).collect(Collectors.toList());
    }
}
