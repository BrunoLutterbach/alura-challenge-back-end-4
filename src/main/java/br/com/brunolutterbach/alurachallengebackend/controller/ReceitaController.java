package br.com.brunolutterbach.alurachallengebackend.controller;

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
    public List<Receita> listarReceitas() {
       return receitaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscarReceita(@PathVariable Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receita.get());
    }

    @PostMapping()
    public ResponseEntity<ReceitaForm> cadastrarReceita(@RequestBody ReceitaForm receitaForm) {
        for (Receita receita : receitaRepository.findAll()) {
            if (receita.getDescricao().equals(receitaForm.getDescricao())
                    && receita.getData().getMonth().equals(receitaForm.getData().getMonth())) {
                return ResponseEntity.badRequest().build();
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




