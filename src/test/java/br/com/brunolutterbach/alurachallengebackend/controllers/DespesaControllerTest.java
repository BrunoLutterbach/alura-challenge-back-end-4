package br.com.brunolutterbach.alurachallengebackend.controllers;


import br.com.brunolutterbach.alurachallengebackend.enums.Categoria;
import br.com.brunolutterbach.alurachallengebackend.model.Despesa;
import br.com.brunolutterbach.alurachallengebackend.repository.DespesaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "teste@email.com", password = "teste123")
public class DespesaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    DespesaRepository despesaRepository;

    @BeforeEach
    public void start() {
        Despesa despesa1 = new Despesa("Pizza", 100.0, LocalDate.of(2022, 1, 1), Categoria.ALIMENTACAO);
        despesaRepository.save(despesa1);
    }

    @Test
    public void deveRetornar200AoConsultarDespesas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/despesas"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRetornar200AoConsultarDespesaPorId() throws Exception {
        List<Despesa> id = despesaRepository.findIdByDescricaoContainingIgnoreCase("pizza");
        mockMvc.perform(MockMvcRequestBuilders.get("/despesas/" + id.get(0).getId(), 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveriaRetornar201AoCadastrar() throws Exception {
        URI uri = new URI("/despesas");
        String json = "{\"descricao\": \"Feijão\",\"valor\": 90.0,\"data\": \"05/01/2022\",\"categoria\": \"ALIMENTACAO\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveriaRetornar200AoAtualizar() throws Exception {
        List<Despesa> id = despesaRepository.findIdByDescricaoContainingIgnoreCase("pizza");
        URI uri = new URI("/despesas" + "/" + id.get(0).getId());
        String json = "{\"descricao\": \"Feijão\",\"valor\": 90.0,\"data\": \"05/01/2022\",\"categoria\": \"ALIMENTACAO\"}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveriaRetornar409SeDescricaoExisteNoMes() throws Exception {
        URI uri = new URI("/despesas");
        String json = "{\"descricao\": \"Pizza\",\"valor\": 90.0,\"data\": \"05/01/2022\",\"categoria\": \"ALIMENTACAO\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void deveriaRetornar409AoAtualizarDescricaoIgualAQueExisteNoMes() throws Exception {
        List<Despesa> id = despesaRepository.findIdByDescricaoContainingIgnoreCase("pizza");
        URI uri = new URI("/despesas" + "/" + id.get(0).getId());
        String json = "{\"descricao\": \"Pizza\",\"valor\": 90.0,\"data\": \"05/01/2022\",\"categoria\": \"ALIMENTACAO\"}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void deveriaRetornar204AoDeletar() throws Exception {
        List<Despesa> id = despesaRepository.findIdByDescricaoContainingIgnoreCase("pizza");
        URI uri = new URI("/despesas" + "/" + id.get(0).getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deveriaRetornar404AoDeletarIdNaoExistente() throws Exception {
        URI uri = new URI("/despesas/1");

        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deveriaRetornar404AoConsultarIdNaoExistente() throws Exception {
        URI uri = new URI("/despesas/1");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @AfterEach
    public void finish() {
        despesaRepository.deleteAll();
    }
}
