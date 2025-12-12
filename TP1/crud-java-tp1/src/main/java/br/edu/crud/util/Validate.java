package br.edu.crud.util;
import java.util.regex.Pattern;
public final class Validate {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private Validate(){}
    public static void nomeValido(String nome) {
        if (nome == null || nome.trim().length() < 2) {
            throw new IllegalArgumentException("Nome inválido");
        }
    }
    public static void emailValido(String email) {
        if (email == null || !EMAIL.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
    }
}