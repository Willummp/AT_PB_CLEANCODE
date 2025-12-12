package org.example.tp2pb.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Builder para criar dados de teste de eventos.
 * Facilita a criação de cenários de teste diversos.
 */
public class TestDataBuilder {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * Dados de um evento válido padrão.
   */
  public static class EventoData {
    public final String nome;
    public final String data;
    public final String local;

    public EventoData(String nome, String data, String local) {
      this.nome = nome;
      this.data = data;
      this.local = local;
    }
  }

  /**
   * Cria dados de um evento válido completo.
   */
  public static EventoData eventoValido() {
    return new EventoData(
        "Workshop de Testes Automatizados",
        LocalDate.now().plusDays(30).format(FORMATTER),
        "Auditório Principal");
  }

  /**
   * Cria dados de evento customizado.
   */
  public static EventoData eventoCustomizado(String nome, String data, String local) {
    return new EventoData(nome, data, local);
  }

  /**
   * Cria dados de evento com nome vazio.
   */
  public static EventoData eventoComNomeVazio() {
    return new EventoData(
        "",
        LocalDate.now().plusDays(7).format(FORMATTER),
        "Local Teste");
  }

  /**
   * Cria dados de evento com nome muito curto (< 3 caracteres).
   */
  public static EventoData eventoComNomeCurto() {
    return new EventoData(
        "AB",
        LocalDate.now().plusDays(7).format(FORMATTER),
        "Local Teste");
  }

  /**
   * Cria dados de evento com nome muito longo (> 100 caracteres).
   */
  public static EventoData eventoComNomeLongo() {
    String nomeLongo = "A".repeat(101);
    return new EventoData(
        nomeLongo,
        LocalDate.now().plusDays(7).format(FORMATTER),
        "Local Teste");
  }

  /**
   * Cria dados de evento com data no passado.
   */
  public static EventoData eventoComDataPassada() {
    return new EventoData(
        "Evento Passado",
        LocalDate.now().minusDays(1).format(FORMATTER),
        "Local Teste");
  }

  /**
   * Cria dados de evento com local vazio.
   */
  public static EventoData eventoComLocalVazio() {
    return new EventoData(
        "Evento Teste",
        LocalDate.now().plusDays(7).format(FORMATTER),
        "");
  }

  /**
   * Cria dados de evento com local muito curto.
   */
  public static EventoData eventoComLocalCurto() {
    return new EventoData(
        "Evento Teste",
        LocalDate.now().plusDays(7).format(FORMATTER),
        "AB");
  }

  /**
   * Cria múltiplos eventos válidos para testes de listagem.
   */
  public static EventoData[] multiplosEventosValidos() {
    LocalDate hoje = LocalDate.now();
    return new EventoData[] {
        new EventoData("Workshop Java", hoje.plusDays(10).format(FORMATTER), "Sala 101"),
        new EventoData("Seminário Spring Boot", hoje.plusDays(20).format(FORMATTER), "Auditório 1"),
        new EventoData("Palestra Selenium", hoje.plusDays(15).format(FORMATTER), "Online"),
        new EventoData("Curso de TDD", hoje.plusDays(30).format(FORMATTER), "Laboratório 2")
    };
  }
}
