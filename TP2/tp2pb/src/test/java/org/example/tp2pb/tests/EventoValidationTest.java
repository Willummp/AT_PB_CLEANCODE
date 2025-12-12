package org.example.tp2pb.tests;

import org.example.tp2pb.config.TestConfig;
import org.example.tp2pb.pages.EventoFormPage;
import org.example.tp2pb.pages.EventoListPage;
import org.example.tp2pb.utils.TestDataBuilder;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite de testes negativos para validação de eventos.
 * Testa cenários de erro e validações de dados.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventoValidationTest extends BaseSeleniumTest {

  private EventoListPage eventoListPage;

  @BeforeEach
  public void setupTest() {
    driver.get(TestConfig.BASE_URL);
    eventoListPage = new EventoListPage(driver);
  }

  /**
   * Testes de validação de nome.
   */
  @Nested
  @DisplayName("Validação do Campo Nome")
  class ValidacaoNome {

    @Test
    @DisplayName("Não deve permitir cadastro com nome vazio")
    public void naoDevePermitirNomeVazio() {
      // Arrange
      var dadosInvalidos = TestDataBuilder.eventoComNomeVazio();

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");
      assertTrue(formPage.temErroNoCampo("nome"), "Campo nome deveria ter erro");

      var mensagemErro = formPage.getMensagemErroDoCampo("nome");
      assertThat(mensagemErro).contains(TestConfig.ERRO_NOME_OBRIGATORIO);
    }

    @Test
    @DisplayName("Não deve permitir nome muito curto (< 3 caracteres)")
    public void naoDevePermitirNomeCurto() {
      // Arrange
      var dadosInvalidos = TestDataBuilder.eventoComNomeCurto();

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");
      assertTrue(formPage.temErroNoCampo("nome"), "Campo nome deveria ter erro");

      var mensagemErro = formPage.getMensagemErroDoCampo("nome");
      assertThat(mensagemErro).contains(TestConfig.ERRO_NOME_MIN_LENGTH);
    }

    @Test
    @DisplayName("Não deve permitir nome muito longo (> 100 caracteres)")
    public void naoDevePermitirNomeLongo() {
      // Arrange
      var dadosInvalidos = TestDataBuilder.eventoComNomeLongo();

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");
      assertTrue(formPage.temErroNoCampo("nome"), "Campo nome deveria ter erro");
    }
  }

  /**
   * Testes de validação de data.
   */
  @Nested
  @DisplayName("Validação do Campo Data")
  class ValidacaoData {

    @Test
    @DisplayName("Não deve permitir data no passado")
    public void naoDevePermitirDataPassada() {
      // Arrange
      var dadosInvalidos = TestDataBuilder.eventoComDataPassada();

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");
      assertTrue(formPage.temErroNoCampo("data"), "Campo data deveria ter erro");

      var mensagemErro = formPage.getMensagemErroDoCampo("data");
      assertThat(mensagemErro).contains(TestConfig.ERRO_DATA_PASSADA);
    }

    @Test
    @DisplayName("Não deve permitir data vazia")
    public void naoDevePermitirDataVazia() {
      // Arrange
      var dadosInvalidos = TestDataBuilder.eventoCustomizado("Evento Teste", "", "Local Teste");

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");
      // Nota: campo HTML5 date impede envio sem valor, então teste é mais superficial
    }
  }

  /**
   * Testes de validação de local.
   */
  @Nested
  @DisplayName("Validação do Campo Local")
  class ValidacaoLocal {

    @Test
    @DisplayName("Não deve permitir local vazio")
    public void naoDevePermitirLocalVazio() {
      // Arrange
      var dadosInvalidos = TestDataBuilder.eventoComLocalVazio();

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");
      assertTrue(formPage.temErroNoCampo("local"), "Campo local deveria ter erro");

      var mensagemErro = formPage.getMensagemErroDoCampo("local");
      assertThat(mensagemErro).contains(TestConfig.ERRO_LOCAL_OBRIGATORIO);
    }

    @Test
    @DisplayName("Não deve permitir local muito curto (< 3 caracteres)")
    public void naoDevePermitirLocalCurto() {
      // Arrange
      var dadosInvalidos = TestDataBuilder.eventoComLocalCurto();

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");
      assertTrue(formPage.temErroNoCampo("local"), "Campo local deveria ter erro");
    }
  }

  /**
   * Testes de validação com múltiplos campos inválidos.
   */
  @Nested
  @DisplayName("Validação de Múltiplos Campos")
  class ValidacaoMultiplosCampos {

    @Test
    @DisplayName("Deve exibir erros para todos os campos vazios")
    public void deveExibirErrosParaTodosCamposVazios() {
      // Arrange - todos os campos vazios
      var dadosInvalidos = TestDataBuilder.eventoCustomizado("", "", "");

      // Act
      var formPage = eventoListPage.clicarAdicionarNovoEvento();
      formPage.preencherFormulario(dadosInvalidos.nome, dadosInvalidos.data, dadosInvalidos.local);
      formPage.submeterFormularioEsperandoErro();

      // Assert
      assertTrue(formPage.temErrosDeValidacao(), "Deveria haver erros de validação");

      var mensagens = formPage.getMensagensErro();
      assertThat(mensagens).hasSizeGreaterThanOrEqualTo(2);

      // Verifica que há erros nos campos principais
      assertTrue(formPage.temErroNoCampo("nome"), "Campo nome deveria ter erro");
      assertTrue(formPage.temErroNoCampo("local"), "Campo local deveria ter erro");
    }

    @Test
    @DisplayName("Deve permitir cancelar formulário com erros")
    public void devePermitirCancelarFormularioComErros() {
      // Arrange
      var formPage = eventoListPage.clicarAdicionarNovoEvento();

      // Act - Cancela sem preencher
      var listPageResult = formPage.clicarCancelar();

      // Assert
      assertThat(listPageResult).isNotNull();
      // Deve voltar para a lista sem adicionar evento
    }
  }
}
