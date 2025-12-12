package br.edu.crud.cli;
import br.edu.crud.repository.ClienteRepository;
import br.edu.crud.service.ClienteService;
import br.edu.crud.model.Cliente;
import br.edu.crud.exception.NotFoundException;
import java.util.Scanner;
import java.util.List;
public class ClienteCli {
    private final ClienteService service;
    private final Scanner sc = new Scanner(System.in);
    public ClienteCli(ClienteService service) { this.service = service; }
    public void start() {
        while (true) {
            System.out.println("Comandos: criar | listar | ver <id> | atualizar-nome <id> <nome> | atualizar-email <id> <email> | deletar <id> | sair");
            String line = sc.nextLine();
            if (line == null) break;
            String[] parts = line.strip().split("\s+", 3);
            try {
                switch (parts[0]) {
                    case "criar" -> {
                        System.out.print("nome: ");
                        String nome = sc.nextLine();
                        System.out.print("email: ");
                        String email = sc.nextLine();
                        Cliente c = service.criarCliente(nome, email);
                        System.out.println("Criado: " + c.id());
                    }
                    case "listar" -> { service.listar().forEach(System.out::println); }
                    case "ver" -> { String id = parts.length>=2 ? parts[1] : ""; System.out.println(service.obterPorId(id)); }
                    case "atualizar-nome" -> {
                        if (parts.length<3) { System.out.println("Uso: atualizar-nome <id> <nome>"); break; }
                        System.out.println(service.atualizarNome(parts[1], parts[2]));
                    }
                    case "atualizar-email" -> {
                        if (parts.length<3) { System.out.println("Uso: atualizar-email <id> <email>"); break; }
                        System.out.println(service.atualizarEmail(parts[1], parts[2]));
                    }
                    case "deletar" -> { System.out.println("Removido: " + service.deletar(parts[1])); }
                    case "sair" -> { return; }
                    default -> System.out.println("Comando desconhecido.");
                }
            } catch (IllegalArgumentException | NotFoundException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }
}