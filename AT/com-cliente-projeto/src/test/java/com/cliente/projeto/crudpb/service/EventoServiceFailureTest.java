package com.cliente.projeto.crudpb.service;

import com.cliente.projeto.crudpb.exception.GlobalExceptionHandler;
import com.cliente.projeto.crudpb.model.Evento;
import com.cliente.projeto.crudpb.model.Usuario;
import com.cliente.projeto.crudpb.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.QueryTimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoServiceFailureTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private EventoService eventoService;

    @Test
    void deveLancarExcecao_QuandoOcorrerTimeoutNoBanco() {
        // Arrange
        Usuario usuario = new Usuario("User", "user@test.com");
        Evento evento = new Evento("Evento Teste", "Descricao");
        
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        
        // Simula falha de Timeout do Banco de Dados (Network Latency/Lock)
        when(eventoRepository.save(any(Evento.class)))
            .thenThrow(new QueryTimeoutException("Simulação de Timeout de conexão"));

        // Act & Assert
        // Verifica se o serviço propaga o erro corretamente (fail gracefully logic handles upstream)
        assertThrows(QueryTimeoutException.class, () -> {
            eventoService.criarEvento(evento, 1L);
        });
    }
}
