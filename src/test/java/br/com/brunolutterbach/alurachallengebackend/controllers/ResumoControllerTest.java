package br.com.brunolutterbach.alurachallengebackend.controllers;

import br.com.brunolutterbach.alurachallengebackend.enums.Categoria;
import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import br.com.brunolutterbach.alurachallengebackend.model.Receita;
import br.com.brunolutterbach.alurachallengebackend.repository.DespesaRepository;
import br.com.brunolutterbach.alurachallengebackend.repository.ReceitaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "teste@email.com", password = "teste123")
public class ResumoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    ReceitaRepository receitaRepository;

    @BeforeEach
    public void start() {
        Receita receita1 = new Receita("Aluguel", 500.0, LocalDate.of(2022, 1, 1));
        receitaRepository.save(receita1);

        Despesa despesa1 = new Despesa("Pizza", 99.0, LocalDate.of(2022, 1, 1), Categoria.ALIMENTACAO);
        despesaRepository.save(despesa1);
    }

    @Test
    public void deveRetornar200AoConsultarResumo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/resumo/01/2022"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void saldoDeveSerReceitasMenosDespesas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/resumo/01/2022"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldo").value(401.0));
    }

    @Test
    public void deveRetornar400AoConsultarResumoComMesInvalido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/resumo/13/2022"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveRetornar404SeNaoExistirReceitasOuDespesas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/resumo/05/2021"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @AfterEach
    public void finish() {
        despesaRepository.deleteAll();
        receitaRepository.deleteAll();
    }

}
