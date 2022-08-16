package br.com.brunolutterbach.alurachallengebackend.DTO;

import br.com.brunolutterbach.alurachallengebackend.enums.Categoria;
import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import lombok.Data;

import java.util.EnumMap;
import java.util.List;

@Data
public class ResumoDTO {

    private Double TotalReceitas = 0.0;
    private Double TotalDespesas = 0.0;
    private Double Saldo = 0.0;
    private EnumMap<Categoria, Double> categorias = new EnumMap<>(Categoria.class);

    public ResumoDTO(List<Receita> receitas, List<Despesa> despesas) {

        this.TotalReceitas = receitas.stream().mapToDouble(Receita::getValor).sum();
        this.TotalDespesas = despesas.stream().mapToDouble(Despesa::getValor).sum();

        for (Despesa despesa : despesas) {
            this.categorias.put(despesa.getCategoria(), // Chave
                    this.categorias.getOrDefault(despesa.getCategoria(), 0.0) + despesa.getValor());
            // this.categorias.getOrDefault(despesa.getCategoria(), 0.0) + despesa.getValor()); Retorna  0.0 se não existir a chave
        }
        this.Saldo = this.TotalReceitas - this.TotalDespesas;
    }
}
