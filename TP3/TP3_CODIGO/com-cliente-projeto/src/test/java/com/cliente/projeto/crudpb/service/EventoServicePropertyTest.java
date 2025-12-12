package com.cliente.projeto.crudpb.service;

import com.cliente.projeto.crudpb.model.Evento;
import com.cliente.projeto.crudpb.repository.EventoRepository;
import net.jqwik.api.*;
import net.jqwik.api.arbitraries.StringArbitrary;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoServicePropertyTest {

    @Property(tries = 100)
    void criarEventoComNomesValidos(@ForAll("nomesValidos") String nome,
            @ForAll("descricoesValidas") String descricao) {
        EventoRepository repository = org.mockito.Mockito.mock(EventoRepository.class);
        EventoService service = new EventoService(repository);

        Evento novoEvento = new Evento(nome, descricao);
        novoEvento.setId(1L);

        when(repository.findByNome(nome)).thenReturn(Optional.empty());
        when(repository.save(any(Evento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Evento salvo = service.criarEvento(novoEvento);

        assert salvo.getNome().equals(nome);
        assert salvo.getDescricao().equals(descricao);
    }

    @Provide
    Arbitrary<String> nomesValidos() {
        return Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(100);
    }

    @Provide
    Arbitrary<String> descricoesValidas() {
        return Arbitraries.strings().withCharRange('a', 'z').ofMaxLength(255);
    }
}
