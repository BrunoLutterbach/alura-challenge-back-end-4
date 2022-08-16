package br.com.brunolutterbach.alurachallengebackend.controller;

import br.com.brunolutterbach.alurachallengebackend.DTO.DespesaDTO;
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
    public List<DespesaDTO> listarDespesas(@RequestParam(required = false) String descricao) {
        if (descricao == null) {
            return DespesaDTO.converter(despesaRepository.findAll());
        }
        return DespesaDTO.converter(despesaRepository.findByDescricaoContainingIgnoreCase(descricao));
    }

    @GetMapping("/{mes}/{ano}")
    public List<DespesaDTO> listarDespesasPorMesEAno(@PathVariable int mes, @PathVariable int ano) {
        List<Despesa> despesa = despesaRepository.findByMesEAno(mes, ano);
        if(despesa.isEmpty()) {
            throw new RuntimeException("Nenhuma despesa encontrada para o mês e ano informados");
        }
        return DespesaDTO.converter(despesa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> buscarDespesa(@PathVariable Long id) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DespesaDTO(despesa.get()));
    }

    @PostMapping()
    public ResponseEntity<DespesaForm> cadastrarDespesa(@RequestBody DespesaForm despesaForm) {
        for (Despesa despesaDB : despesaRepository.findAll()) {
            if (despesaDB.getDescricao().equals(despesaForm.getDescricao()) &&
                    despesaDB.getData().getMonth().equals(despesaForm.getData().getMonth())) {
                return ResponseEntity.status(409).build();
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
