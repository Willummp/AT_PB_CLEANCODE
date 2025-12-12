package org.example.tp2pb.tests;

import org.example.tp2pb.config.TestConfig;
import org.example.tp2pb.pages.EventoFormPage;
import org.example.tp2pb.pages.EventoListPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite de testes parametrizados para validar múltiplos cenários.
 */
public class EventoParameterizedTest extends BaseSeleniumTest {

  private EventoListPage eventoListPage;
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @BeforeEach
  public void setupTest() {
    driver.get(TestConfig.BASE_URL);
    eventoListPage = new EventoListPage(driver);
  }

  /**
   * Testa cadastro com múltiplos eventos válidos usando @CsvSource.
   */
  @ParameterizedTest(name = "Deve cadastrar evento: {0}")
  @DisplayName("Cadastro parametrizado de eventos válidos")
  @CsvSource({
      "Workshop de Java Avançado, 2025-12-10, Sala 101",
      "Seminário Spring Boot, 2026-01-15, Auditório Central",
      "Palestra sobre Testes, 2025-11-25, Online",
      "Curso de Clean Code, 2026-02-01, Laboratório 3",
      "Hackathon 2025, 2025-12-28, Centro de Inovação"
  })
  public void deveCadastrarEventosValidosParametrizados(String nome, String data, String local) {
    // Act
    EventoFormPage formPage = eventoListPage.clicarAdicionarNovoEvento();
    formPage.preencherFormulario(nome, data, local);
    EventoListPage listPageResult = formPage.submeterFormulario();

    // Assert
    assertTrue(listPageResult.isEventoPresenteNaLista(nome),
        "Evento " + nome + " não foi encontrado na lista");
  }

  /**
   * Testa validação de nomes inválidos usando @CsvSource.
   */
  @ParameterizedTest(name = "Deve rejeitar nome inválido: ''{0}''")
  @DisplayName("Validação parametrizada de nomes inválidos")
  @CsvSource({
      "'', Nome do evento é obrigatório",
      "AB, Nome deve ter entre 3 e 100 caracteres",
      "A, Nome deve ter entre 3 e 100 caracteres"
  })
  public void deveRejeitarNomesInvalidos(String nomeInvalido, String mensagemEsperada) {
    // Arrange
    String dataValida = LocalDate.now().plusDays(10).format(FORMATTER);

    // Act
    EventoFormPage formPage = eventoListPage.clicarAdicionarNovoEvento();
    formPage.preencherFormulario(nomeInvalido, dataValida, "Local Teste");
    formPage.submeterFormularioEsperandoErro();

    // Assert
    assertTrue(formPage.temErroNoCampo("nome"), "Campo nome deveria ter erro");
    String mensagemReal = formPage.getMensagemErroDoCampo("nome");
    assertThat(mensagemReal).contains(mensagemEsperada);
  }

  /**
   * Testa validação de locais inválidos usando @CsvSource.
   */
  @ParameterizedTest(name = "Deve rejeitar local inválido: ''{0}''")
  @DisplayName("Validação parametrizada de locais inválidos")
  @CsvSource({
      "'', Local do evento é obrigatório",
      "AB, Local deve ter entre 3 e 150 caracteres",
      "X, Local deve ter entre 3 e 150 caracteres"
  })
  public void deveRejeitarLocaisInvalidos(String localInvalido, String mensagemEsperada) {
    // Arrange
    String dataValida = LocalDate.now().plusDays(10).format(FORMATTER);

    // Act
    EventoFormPage formPage = eventoListPage.clicarAdicionarNovoEvento();
    formPage.preencherFormulario("Evento Teste Parametrizado", dataValida, localInvalido);
    formPage.submeterFormularioEsperandoErro();

    // Assert
    assertTrue(formPage.temErroNoCampo("local"), "Campo local deveria ter erro");
    String mensagemReal = formPage.getMensagemErroDoCampo("local");
    assertThat(mensagemReal).contains(mensagemEsperada);
  }

  /**
   * Fonte de dados para testes com @MethodSource.
   * Retorna eventos válidos com diferentes características.
   */
  static Stream<EventoData> eventosValidosProvider() {
    LocalDate hoje = LocalDate.now();
    return Stream.of(
        new EventoData("Conferência de Tecnologia", hoje.plusMonths(1), "Centro de Convenções"),
        new EventoData("Workshop de DevOps", hoje.plusMonths(2), "Online"),
        new EventoData("Meetup de Desenvolvedores", hoje.plusWeeks(2), "Coworking Space"),
        new EventoData("Treinamento de Segurança", hoje.plusDays(15), "Sala de Treinamento"),
        new EventoData("Apresentação de Projetos", hoje.plusMonths(3), "Auditório Principal"));
  }

  /**
   * Testa cadastro usando @MethodSource para cenários mais complexos.
   */
  @ParameterizedTest(name = "Cadastrar via MethodSource: {0}")
  @DisplayName("Cadastro parametrizado usando MethodSource")
  @MethodSource("eventosValidosProvider")
  public void deveCadastrarEventosViaMethodSource(EventoData eventoData) {
    // Act
    EventoFormPage formPage = eventoListPage.clicarAdicionarNovoEvento();
    formPage.preencherFormulario(
        eventoData.nome,
        eventoData.data.format(FORMATTER),
        eventoData.local);
    EventoListPage listPageResult = formPage.submeterFormulario();

    // Assert
    assertTrue(listPageResult.isEventoPresenteNaLista(eventoData.nome),
        "Evento " + eventoData.nome + " não encontrado");
  }

  /**
   * Classe auxiliar para dados de evento parametrizado.
   */
  static class EventoData {
    final String nome;
    final LocalDate data;
    final String local;

    EventoData(String nome, LocalDate data, String local) {
      this.nome = nome;
      this.data = data;
      this.local = local;
    }

    @Override
    public String toString() {
      return nome;
    }
  }
}
