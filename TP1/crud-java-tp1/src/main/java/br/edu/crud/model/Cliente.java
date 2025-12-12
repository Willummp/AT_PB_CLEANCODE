package br.edu.crud.model;
import java.util.Objects;
public final class Cliente {
    private final String id;
    private final String nome;
    private final String email;

    public Cliente(String id, String nome, String email) {
        this.id = Objects.requireNonNull(id, "id");
        this.nome = Objects.requireNonNull(nome, "nome");
        this.email = Objects.requireNonNull(email, "email");
    }

    public String id() { return id; }
    public String nome() { return nome; }
    public String email() { return email; }

    public Cliente comNome(String novoNome) { return new Cliente(id, novoNome, email); }
    public Cliente comEmail(String novoEmail) { return new Cliente(id, nome, novoEmail); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente c = (Cliente) o;
        return id.equals(c.id);
    }

    @Override public int hashCode() { return id.hashCode(); }

    @Override public String toString() {
        return String.format("Cliente{id=%s, nome=%s, email=%s}", id, nome, email);
    }
}