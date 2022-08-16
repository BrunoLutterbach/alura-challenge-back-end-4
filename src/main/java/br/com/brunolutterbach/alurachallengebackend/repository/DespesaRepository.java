package br.com.brunolutterbach.alurachallengebackend.repository;

import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("select r from Despesa r where MONTH(r.data) = ?1 and YEAR(r.data) = ?2")
    List<Despesa> findByMesEAno(int mes, int ano);


    @Query("select sum(r.valor) from Despesa r where MONTH(r.data) = ?1 and YEAR(r.data) = ?2")
    Double despesasDoMes(int mes, int ano);

}

