package br.com.brunolutterbach.alurachallengebackend.controller;

import br.com.brunolutterbach.alurachallengebackend.DTO.ReceitaDTO;
import br.com.brunolutterbach.alurachallengebackend.DTO.form.ReceitaForm;
import br.com.brunolutterbach.alurachallengebackend.DTO.form.UpdateReceitaForm;
import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import br.com.brunolutterbach.alurachallengebackend.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaRepository receitaRepository;

    @GetMapping()
    public List<ReceitaDTO> listarReceitas(@RequestParam(required = false) String descricao) {
        if (descricao == null) {
            return ReceitaDTO.converter(receitaRepository.findAll());
        }
        return ReceitaDTO.converter(receitaRepository.findByDescricaoContainingIgnoreCase(descricao));
    }

    @GetMapping("/{mes}/{ano}")
    public List<ReceitaDTO> listarReceitasPorMesEAno(@PathVariable int mes, @PathVariable int ano) {
        List<Receita> receita = receitaRepository.findByMesEAno(mes, ano);
        if(receita.isEmpty()) {
            throw new RuntimeException("Nenhuma receita encontrada para o mês e ano informados");
        }
        return ReceitaDTO.converter(receita);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> buscarReceita(@PathVariable Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ReceitaDTO(receita.get()));
    }

    @PostMapping()
    public ResponseEntity<ReceitaForm> cadastrarReceita(@RequestBody ReceitaForm receitaForm) {
        for (Receita receita : receitaRepository.findAll()) {
            if (receita.getDescricao().equals(receitaForm.getDescricao())
                    && receita.getData().getMonth().equals(receitaForm.getData().getMonth())) {
                return ResponseEntity.status(409).build();
            }
        }
        Receita receita = receitaForm.converter(new Receita());
        receitaRepository.save(receita);
        return ResponseEntity.ok(receitaForm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateReceitaForm> atualizarReceita(@PathVariable Long id, @RequestBody UpdateReceitaForm updateReceitaForm) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        for (Receita receitaDB : receitaRepository.findAll()) {
            if (receitaDB.getDescricao().equals(updateReceitaForm.getDescricao()) &&
                    receitaDB.getData().getMonth().equals(updateReceitaForm.getData().getMonth())) {
                return ResponseEntity.badRequest().build();
            }
        }
        Receita receitaAtualizada = updateReceitaForm.update(id, receitaRepository);
        receitaRepository.save(receitaAtualizada);
        return ResponseEntity.ok(updateReceitaForm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        receitaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}




