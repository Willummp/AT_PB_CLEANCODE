package br.edu.crud.repository;
import br.edu.crud.model.Cliente;
import java.util.*;
public class ClienteRepository {
    private final Map<String, Cliente> storage = new LinkedHashMap<>();
    public Optional<Cliente> buscarPorId(String id) { return Optional.ofNullable(storage.get(id)); }
    public List<Cliente> listarTodos() { return new ArrayList<>(storage.values()); }
    public Cliente salvar(Cliente cliente) { storage.put(cliente.id(), cliente); return cliente; }
    public Cliente atualizar(String id, Cliente novo) { storage.put(id, novo); return novo; }
    public Cliente remover(String id) { return storage.remove(id); }
    public void limpar() { storage.clear(); }
}