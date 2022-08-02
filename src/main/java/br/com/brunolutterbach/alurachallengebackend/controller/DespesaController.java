package br.com.brunolutterbach.alurachallengebackend.controller;

import br.com.brunolutterbach.alurachallengebackend.DTO.form.DespesaForm;
import br.com.brunolutterbach.alurachallengebackend.DTO.form.UpdateDespesaForm;
import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import br.com.brunolutterbach.alurachallengebackend.repository.DespesaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaRepository despesaRepository;

    @GetMapping()
    public List<Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> buscarDespesa(@PathVariable Long id) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(despesa.get());
    }

    @PostMapping()
    public ResponseEntity<DespesaForm> cadastrarDespesa(@RequestBody DespesaForm despesaForm) {
        for (Despesa despesa : despesaRepository.findAll()) {
            if (despesa.getDescricao().equals(despesaForm.getDescricao()) &&
                    despesa.getData().getMonth().equals(despesaForm.getData().getMonth())) {
                return ResponseEntity.badRequest().build();
            }
        }
        Despesa despesa = despesaForm.converter(new Despesa());
        despesaRepository.save(despesa);
        return ResponseEntity.ok(despesaForm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateDespesaForm> atualizarDespesa(@PathVariable Long id, @RequestBody UpdateDespesaForm updateDespesaForm) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        for (Despesa despesaDB : despesaRepository.findAll()) {
            if (despesaDB.getDescricao().equals(updateDespesaForm.getDescricao()) &&
                    despesaDB.getData().getMonth().equals(updateDespesaForm.getData().getMonth())) {
                return ResponseEntity.badRequest().build();
            }
        }
        Despesa despesaAtualizada = updateDespesaForm.update(id, despesaRepository);
        despesaRepository.save(despesaAtualizada);
        return ResponseEntity.ok(updateDespesaForm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        despesaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
