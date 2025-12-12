package br.edu.crud.service;
import br.edu.crud.model.Cliente;
import br.edu.crud.repository.ClienteRepository;
import br.edu.crud.exception.NotFoundException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
class ClienteServiceTest {
    ClienteRepository repo;
    ClienteService service;
    @BeforeEach void setup() { repo = new ClienteRepository(); service = new ClienteService(repo); }
    @Test void criarEObter() {
        Cliente c = service.criarCliente("Ana", "ana@ex.com");
        Cliente encontrado = service.obterPorId(c.id());
        assertEquals("Ana", encontrado.nome());
        assertEquals("ana@ex.com", encontrado.email());
    }
    @Test void atualizarNomeEEmail() {
        Cliente c = service.criarCliente("Joao", "j@e.com");
        service.atualizarNome(c.id(), "João Silva");
        service.atualizarEmail(c.id(), "joao.silva@ex.com");
        Cliente atual = service.obterPorId(c.id());
        assertEquals("João Silva", atual.nome());
        assertEquals("joao.silva@ex.com", atual.email());
    }
    @Test void deletarLancaNotFoundDepois() {
        Cliente c = service.criarCliente("Xavier", "x@x.com");
        service.deletar(c.id());
        assertThrows(NotFoundException.class, () -> service.obterPorId(c.id()));
    }
    @Test void criarComEmailInvalidoLanca() {
        assertThrows(IllegalArgumentException.class, () -> service.criarCliente("Nome", "invalido"));
    }
}