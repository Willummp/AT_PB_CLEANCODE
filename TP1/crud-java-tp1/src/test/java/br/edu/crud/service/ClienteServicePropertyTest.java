package br.edu.crud.service;
import br.edu.crud.repository.ClienteRepository;
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import net.jqwik.web.api.Email;

import static org.junit.jupiter.api.Assertions.*;
class ClienteServicePropertyTest {
    @Property
    void criarComNomesValidos(@ForAll @AlphaChars @StringLength(min=2, max=50) String nome,
                              @ForAll @Email String email) {
        var repo = new ClienteRepository();
        var service = new ClienteService(repo);
        var c = service.criarCliente(nome, email);
        assertNotNull(c.id());
        assertEquals(nome.trim(), c.nome());
        assertEquals(email.trim(), c.email());
    }
}