package br.com.brunolutterbach.alurachallengebackend.controller;

import br.com.brunolutterbach.alurachallengebackend.DTO.ResumoDTO;
import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import br.com.brunolutterbach.alurachallengebackend.repository.DespesaRepository;
import br.com.brunolutterbach.alurachallengebackend.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/resumo")
@AllArgsConstructor
public class ResumoController {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    @GetMapping("{mes}/{ano}")
    public ResponseEntity<ResumoDTO> resumoDoMes(@PathVariable int mes, @PathVariable int ano) {
        List<Receita> receitas = receitaRepository.findByMesEAno(mes, ano);
        List<Despesa> despesas = despesaRepository.findByMesEAno(mes, ano);

        ResumoDTO resumoDTO = new ResumoDTO(receitas, despesas);
        return ResponseEntity.ok(resumoDTO);
    }
}
