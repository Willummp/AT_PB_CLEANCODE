package com.cliente.projeto.crudpb.fuzz;

import com.cliente.projeto.crudpb.model.Evento;
import com.cliente.projeto.crudpb.model.Usuario;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import net.jqwik.api.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EventoFuzzTest {

    private final Validator validator;

    public EventoFuzzTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Property
    void eventoDeveFalharComNomesInvalidos(@ForAll("nomesInvalidos") String nomeInv) {
        Usuario usuario = new Usuario("User", "email@test.com");
        Evento evento = new Evento();
        evento.setNome(nomeInv);
        evento.setDescricao("Descricao valida");
        evento.setUsuario(usuario);

        Set<?> violacoes = validator.validate(evento);
        assertThat(violacoes).isNotEmpty();
    }

    @Property
    void eventoDevePassarComNomesValidos(@ForAll("nomesValidos") String nomeVal) {
        Usuario usuario = new Usuario("User", "email@test.com");
        Evento evento = new Evento();
        evento.setNome(nomeVal);
        evento.setDescricao("Descricao");
        evento.setUsuario(usuario);

        Set<?> violacoes = validator.validate(evento);
        assertThat(violacoes).isEmpty();
    }

    @Provide
    Arbitrary<String> nomesInvalidos() {
        // Gera strings nulas, vazias, ou muito curtas/longas
        return Arbitraries.oneOf(
            Arbitraries.strings().withCharRange('a', 'z').ofMinLength(0).ofMaxLength(2), // Muito curto
            Arbitraries.strings().withCharRange('a', 'z').ofMinLength(101).ofMaxLength(500), // Muito longo
            Arbitraries.just(""),
            Arbitraries.just("  ")
        );
    }

    @Provide
    Arbitrary<String> nomesValidos() {
        // Gera strings válidas (3 a 100 chars)
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(3)
                .ofMaxLength(100);
    }
}
