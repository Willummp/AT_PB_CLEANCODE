package com.cliente.projeto.crudpb.service;

import com.cliente.projeto.crudpb.exception.ValidacaoException;
import com.cliente.projeto.crudpb.model.Evento;
import com.cliente.projeto.crudpb.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito
class EventoServiceTest {

    @Mock // Cria um "dublê" do repositório
    private EventoRepository eventoRepository;

    @InjectMocks // Injeta os mocks acima no serviço
    private EventoService eventoService;

    @Test
    void deveLancarExcecao_QuandoCriarEventoComNomeDuplicado() {
        // 1. Cenário (Given)
        // Simulando que o repositório encontrou um evento com este nome
        when(eventoRepository.findByNome("Evento Repetido")).thenReturn(Optional.of(new Evento()));

        Evento eventoNovo = new Evento("Evento Repetido", "Descricao");

        // 2. Ação e Verificação (When & Then)
        // Verificamos se a exceção de validação de negócio é lançada
        assertThrows(ValidacaoException.class, () -> {
            eventoService.criarEvento(eventoNovo);
        });

        // 3. Verificação Extra
        // Garantir que o "fail early" funcionou e NUNCA tentamos salvar
        verify(eventoRepository, never()).save(any());
    }
}