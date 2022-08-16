package br.com.brunolutterbach.alurachallengebackend.repository;

import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("select r from Receita r where MONTH(r.data) = ?1 and YEAR(r.data) = ?2")
    List<Receita> findByMesEAno(int mes, int ano);

    @Query("select sum(r.valor) from Receita r where MONTH(r.data) = ?1 and YEAR(r.data) = ?2")
    Double receitasDoMes(int mes, int ano);

}

