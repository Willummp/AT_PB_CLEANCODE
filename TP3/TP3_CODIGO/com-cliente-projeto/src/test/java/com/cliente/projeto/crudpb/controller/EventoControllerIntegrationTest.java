package com.cliente.projeto.crudpb.controller;

import com.cliente.projeto.crudpb.service.EventoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoController.class)
class EventoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService;

    /**
     * Exemplo de Teste Parametrizado (Requisito Artefato 2)
     * Testa múltiplas entradas inválidas no formulário de criação.
     */
    @ParameterizedTest
    @CsvSource({
            "'', 'Descrição válida', 'O nome é obrigatório.'",
            "'OK', 'Descrição válida', 'O nome deve ter entre 3 e 100 caracteres.'"
    })
    void deveRetornarErroNoFormulario_ParaEntradasInvalidas(String nome, String descricao, String mensagemErroEsperada)
            throws Exception {

        mockMvc.perform(post("/eventos") // Simula um POST para /eventos
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", nome) // Envia dados como um formulário HTML
                .param("descricao", descricao))
                .andExpect(status().isOk())
                .andExpect(view().name("form-evento"))
                .andExpect(model().hasErrors())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(mensagemErroEsperada)));
    }

    @Test
    void deveCriarEventoComSucesso_ParaEntradaValida() throws Exception {
        mockMvc.perform(post("/eventos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", "Evento de Teste Válido")
                .param("descricao", "Uma descrição completa"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/eventos"))
                .andExpect(redirectedUrl("/eventos"))
                .andExpect(flash().attributeExists("mensagemSucesso"));
    }
}