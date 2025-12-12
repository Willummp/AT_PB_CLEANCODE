package br.edu.crud.service;
import br.edu.crud.model.Cliente;
import br.edu.crud.repository.ClienteRepository;
import br.edu.crud.exception.NotFoundException;
import br.edu.crud.util.Validate;
import java.util.List;
import java.util.UUID;
public class ClienteService {
    private final ClienteRepository repo;
    public ClienteService(ClienteRepository repo) { this.repo = repo; }

    public Cliente criarCliente(String nome, String email) {
        Validate.nomeValido(nome);
        Validate.emailValido(email);
        String id = UUID.randomUUID().toString();
        Cliente c = new Cliente(id, nome.trim(), email.trim());
        return repo.salvar(c);
    }
    public Cliente obterPorId(String id) {
        return repo.buscarPorId(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado: " + id));
    }
    public List<Cliente> listar() { return repo.listarTodos(); }
    public Cliente atualizarNome(String id, String novoNome) {
        Validate.nomeValido(novoNome);
        Cliente atual = obterPorId(id);
        Cliente atualizado = atual.comNome(novoNome.trim());
        return repo.atualizar(id, atualizado);
    }
    public Cliente atualizarEmail(String id, String novoEmail) {
        Validate.emailValido(novoEmail);
        Cliente atual = obterPorId(id);
        Cliente atualizado = atual.comEmail(novoEmail.trim());
        return repo.atualizar(id, atualizado);
    }
    public Cliente deletar(String id) {
        Cliente existente = obterPorId(id);
        repo.remover(id);
        return existente;
    }
}