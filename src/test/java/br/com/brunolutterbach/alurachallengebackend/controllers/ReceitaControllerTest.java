package br.com.brunolutterbach.alurachallengebackend.controllers;



import br.com.brunolutterbach.alurachallengebackend.model.Receita;
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
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "teste@email.com", password = "teste123")
public class ReceitaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ReceitaRepository receitaRepository;

    @BeforeEach
    public void start() {
        Receita receita1 = new Receita("Aluguel", 500.0, LocalDate.of(2022, 1, 1));
        receitaRepository.save(receita1);
    }

    @Test
    public void deveRetornar200AoConsultarReceitas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/receitas"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRetornar200AoConsultarReceitaPorId() throws Exception {
        List<Receita> id = receitaRepository.findIdByDescricaoContainingIgnoreCase("aluguel");
        mockMvc.perform(MockMvcRequestBuilders.get("/receitas/" + id.get(0).getId(), 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRetornar200AoConsultarReceitaPorDescricao() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/receitas?descricao=aluguel"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRetornar200AoConsultarReceitaPorData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/receitas/01/2022"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveriaRetornar409SeDescricaoExisteNoMes() throws Exception {
        String json = "{\"descricao\":\"Aluguel\",\"valor\":500.0,\"data\":\"01/01/2022\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/receitas")
                .contentType("application/json")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void deveriaRetornar404SeReceitaNaoExiste() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/receitas/99999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @AfterEach
    public void finish() {
        receitaRepository.deleteAll();
    }
}
