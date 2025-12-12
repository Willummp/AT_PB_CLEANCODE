package br.edu.crud;
import br.edu.crud.cli.ClienteCli;
import br.edu.crud.repository.ClienteRepository;
import br.edu.crud.service.ClienteService;
public class App {
    public static void main(String[] args) {
        var repo = new ClienteRepository();
        var service = new ClienteService(repo);
        new ClienteCli(service).start();
    }
}