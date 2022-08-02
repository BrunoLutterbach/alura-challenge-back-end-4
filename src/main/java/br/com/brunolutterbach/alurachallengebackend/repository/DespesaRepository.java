package br.com.brunolutterbach.alurachallengebackend.repository;

import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

}

