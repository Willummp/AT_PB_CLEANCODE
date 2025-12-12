package org.example.tp2pb.tests;

import org.example.tp2pb.config.TestConfig;
import org.example.tp2pb.pages.EventoFormPage;
import org.example.tp2pb.pages.EventoListPage;
import org.example.tp2pb.utils.ScreenshotHelper;
import org.example.tp2pb.utils.TestDataBuilder;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de testes CRUD para o sistema de eventos.
 * Testa operações de Create, Read, Update e Delete.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventoCrudTest extends BaseSeleniumTest {

    private EventoListPage eventoListPage;

    @BeforeEach
    public void setupTest() {
        driver.get(TestConfig.BASE_URL);
        eventoListPage = new EventoListPage(driver);
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        // Captura screenshot se o teste falhou
        if (testInfo.getTags().contains("screenshot-on-failure")) {
            ScreenshotHelper.captureScreenshot(driver, testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9]", "_"));
        }
    }

    /**
     * Testes de cadastro de eventos.
     */
    @Nested
    @DisplayName("Testes de Cadastro")
    class TestesCadastro {

        @Test
        @Order(1)
        @DisplayName("Deve cadastrar um novo evento com sucesso")
        public void deveCadastrarEventoComSucesso() {
            // Arrange
            var dadosEvento = TestDataBuilder.eventoValido();

            // Act
            var formPage = eventoListPage.clicarAdicionarNovoEvento();
            formPage.preencherFormulario(dadosEvento.nome, dadosEvento.data, dadosEvento.local);
            var listPageResult = formPage.submeterFormulario();

            // Assert
            assertTrue(listPageResult.isEventoPresenteNaLista(dadosEvento.nome),
                    "O evento cadastrado não foi encontrado na lista.");
            assertThat(listPageResult.getQuantidadeEventos()).isGreaterThan(0);
        }

        @Test
        @Order(2)
        @DisplayName("Deve cadastrar múltiplos eventos")
        public void deveCadastrarMultiplosEventos() {
            // Arrange
            var eventos = TestDataBuilder.multiplosEventosValidos();
            int quantidadeInicial = eventoListPage.getQuantidadeEventos();

            // Act
            for (TestDataBuilder.EventoData evento : eventos) {
                var formPage = eventoListPage.clicarAdicionarNovoEvento();
                formPage.preencherFormulario(evento.nome, evento.data, evento.local);
                eventoListPage = formPage.submeterFormulario();
            }

            // Assert
            int quantidadeFinal = eventoListPage.getQuantidadeEventos();
            assertThat(quantidadeFinal).isEqualTo(quantidadeInicial + eventos.length);

            // Verifica que todos os eventos foram cadastrados
            var nomesEventos = eventoListPage.getTodosNomesEventos();
            for (TestDataBuilder.EventoData evento : eventos) {
                assertTrue(nomesEventos.contains(evento.nome),
                        "Evento " + evento.nome + " não encontrado na lista");
            }
        }
    }

    /**
     * Testes de edição de eventos.
     */
    @Nested
    @DisplayName("Testes de Edição")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestesEdicao {

        private final String NOME_EVENTO_ORIGINAL = "Evento Para Editar";
        private final String NOME_EVENTO_EDITADO = "Evento Editado com Sucesso";

        @Test
        @Order(1)
        @DisplayName("Preparação: Criar evento para edição")
        public void prepararEventoParaEdicao() {
            var evento = TestDataBuilder.eventoCustomizado(NOME_EVENTO_ORIGINAL, "2025-12-15", "Sala A");
            var formPage = eventoListPage.clicarAdicionarNovoEvento();
            formPage.preencherFormulario(evento.nome, evento.data, evento.local);
            formPage.submeterFormulario();
        }

        @Test
        @Order(2)
        @DisplayName("Deve editar um evento existente com sucesso")
        public void deveEditarEventoComSucesso() {
            // Arrange - evento já deve existir do teste anterior
            assertTrue(eventoListPage.isEventoPresenteNaLista(NOME_EVENTO_ORIGINAL),
                    "Evento original não encontrado");

            // Act
            var formPage = eventoListPage.editarEvento(NOME_EVENTO_ORIGINAL);
            assertTrue(formPage.isEdicao(), "Formulário deveria estar em modo de edição");

            formPage.preencherFormulario(NOME_EVENTO_EDITADO, "2025-12-20", "Sala B");
            var listPageResult = formPage.submeterFormulario();

            // Assert
            assertTrue(listPageResult.isEventoPresenteNaLista(NOME_EVENTO_EDITADO),
                    "Evento editado não encontrado na lista");
            assertFalse(listPageResult.isEventoPresenteNaLista(NOME_EVENTO_ORIGINAL),
                    "Evento original ainda aparece na lista após edição");
        }
    }

    /**
     * Testes de exclusão de eventos.
     */
    @Nested
    @DisplayName("Testes de Exclusão")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestesExclusao {

        private final String NOME_EVENTO_PARA_EXCLUIR = "Evento Para Excluir";

        @Test
        @Order(1)
        @DisplayName("Preparação: Criar evento para exclusão")
        public void prepararEventoParaExclusao() {
            var evento = TestDataBuilder.eventoCustomizado(NOME_EVENTO_PARA_EXCLUIR, "2025-12-25", "Online");
            var formPage = eventoListPage.clicarAdicionarNovoEvento();
            formPage.preencherFormulario(evento.nome, evento.data, evento.local);
            formPage.submeterFormulario();
        }

        @Test
        @Order(2)
        @DisplayName("Deve excluir um evento existente")
        public void deveExcluirEventoComSucesso() {
            // Arrange
            assertTrue(eventoListPage.isEventoPresenteNaLista(NOME_EVENTO_PARA_EXCLUIR),
                    "Evento para exclusão não encontrado");
            int quantidadeAntes = eventoListPage.getQuantidadeEventos();

            // Act
            eventoListPage.excluirEvento(NOME_EVENTO_PARA_EXCLUIR);

            // Assert
            assertFalse(eventoListPage.isEventoPresenteNaLista(NOME_EVENTO_PARA_EXCLUIR),
                    "O evento ainda é exibido na lista após a exclusão");
            int quantidadeDepois = eventoListPage.getQuantidadeEventos();
            assertThat(quantidadeDepois).isEqualTo(quantidadeAntes - 1);
        }
    }

    /**
     * Testes de listagem e visualização.
     */
    @Nested
    @DisplayName("Testes de Listagem")
    class TestesListagem {

        @Test
        @DisplayName("Deve exibir todos os eventos cadastrados")
        public void deveExibirTodosEventos() {
            // Act
            var nomesEventos = eventoListPage.getTodosNomesEventos();
            int quantidade = eventoListPage.getQuantidadeEventos();

            // Assert
            assertThat(nomesEventos).hasSize(quantidade);
            assertThat(quantidade).isGreaterThanOrEqualTo(0);
        }

        @Test
        @DisplayName("Deve navegar para formulário de novo evento e voltar")
        public void deveNavegarParaFormularioEVoltar() {
            // Act
            EventoFormPage formPage = eventoListPage.clicarAdicionarNovoEvento();

            // Assert
            assertNotNull(formPage);
            assertFalse(formPage.isEdicao(), "Formulário não deveria estar em modo de edição");

            // Volta para a lista
            EventoListPage listPageResult = formPage.clicarCancelar();
            assertNotNull(listPageResult);
        }
    }
}