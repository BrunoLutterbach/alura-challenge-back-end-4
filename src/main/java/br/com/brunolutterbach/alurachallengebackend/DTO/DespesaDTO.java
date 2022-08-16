package br.com.brunolutterbach.alurachallengebackend.DTO;

import br.com.brunolutterbach.alurachallengebackend.enums.Categoria;
import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DespesaDTO {

    private Long id;
    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public DespesaDTO(Despesa despesa) {
        this.id = despesa.getId();
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.data = despesa.getData();
        this.categoria = despesa.getCategoria();
    }

    public DespesaDTO() {
    }

    public static List<DespesaDTO> converter(List<Despesa> despesas) {
        return despesas.stream().map(DespesaDTO::new).collect(Collectors.toList());
    }
}