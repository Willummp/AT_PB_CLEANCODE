package org.example.tp2pb.pages;

import org.example.tp2pb.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object para a página de listagem de eventos.
 * Encapsula a lógica de interação com a página index.html.
 */
public class EventoListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By ADD_EVENTO_BUTTON = By.linkText("Adicionar Novo Evento");
    private static final By TABELA_EVENTOS_BODY = By.cssSelector("table tbody");
    private static final By TABELA_EVENTOS_ROWS = By.cssSelector("table tbody tr");
    private static final By PAGE_TITLE = By.xpath("//h1[text()='Lista de Eventos']");

    /**
     * Construtor que inicializa o Page Object.
     *
     * @param driver instância do WebDriver
     */
    public EventoListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.DEFAULT_TIMEOUT_SECONDS));
        // Aguarda a página carregar
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_TITLE));
    }

    /**
     * Clica no botão "Adicionar Novo Evento".
     *
     * @return objeto EventoFormPage
     */
    public EventoFormPage clicarAdicionarNovoEvento() {
        driver.findElement(ADD_EVENTO_BUTTON).click();
        return new EventoFormPage(driver);
    }

    /**
     * Verifica se um evento está presente na lista pelo nome.
     *
     * @param nomeEvento nome do evento a procurar
     * @return true se o evento está na lista, false caso contrário
     */
    public boolean isEventoPresenteNaLista(String nomeEvento) {
        WebElement tabela = wait.until(ExpectedConditions.visibilityOfElementLocated(TABELA_EVENTOS_BODY));
        return tabela.getText().contains(nomeEvento);
    }

    /**
     * Obtém a quantidade de eventos na tabela.
     *
     * @return número de eventos exibidos
     */
    public int getQuantidadeEventos() {
        List<WebElement> rows = driver.findElements(TABELA_EVENTOS_ROWS);
        return rows.size();
    }

    /**
     * Obtém todos os nomes de eventos exibidos na tabela.
     *
     * @return lista com os nomes dos eventos
     */
    public List<String> getTodosNomesEventos() {
        List<WebElement> rows = driver.findElements(TABELA_EVENTOS_ROWS);
        return rows.stream()
                .map(row -> row.findElement(By.cssSelector("td:first-child")).getText())
                .collect(Collectors.toList());
    }

    /**
     * Clica no botão de editar de um evento específico.
     *
     * @param nomeEvento nome do evento a editar
     * @return objeto EventoFormPage para edição
     */
    public EventoFormPage editarEvento(String nomeEvento) {
        WebElement linhaDoEvento = encontrarLinhaDoEvento(nomeEvento);
        WebElement botaoEditar = linhaDoEvento.findElement(By.className("btn-warning"));
        botaoEditar.click();
        return new EventoFormPage(driver);
    }

    /**
     * Exclui um evento da lista.
     *
     * @param nomeEvento nome do evento a excluir
     */
    public void excluirEvento(String nomeEvento) {
        WebElement linhaDoEvento = encontrarLinhaDoEvento(nomeEvento);
        WebElement botaoExcluir = linhaDoEvento.findElement(By.className("btn-danger"));
        botaoExcluir.click();

        // Aceita o alerta de confirmação do JavaScript
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // Aguarda o evento desaparecer da lista para evitar race conditions
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElementLocated(TABELA_EVENTOS_BODY, nomeEvento)));
    }

    /**
     * Método auxiliar para encontrar a linha de um evento na tabela.
     * Usa CSS Selector mais robusto ao invés de XPath frágil.
     *
     * @param nomeEvento nome do evento
     * @return WebElement da linha do evento
     */
    private WebElement encontrarLinhaDoEvento(String nomeEvento) {
        List<WebElement> rows = driver.findElements(TABELA_EVENTOS_ROWS);
        return rows.stream()
                .filter(row -> row.findElement(By.cssSelector("td:first-child")).getText().equals(nomeEvento))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado na lista: " + nomeEvento));
    }

    /**
     * Verifica se a página está vazia (sem eventos).
     *
     * @return true se não há eventos, false caso contrário
     */
    public boolean isListaVazia() {
        return getQuantidadeEventos() == 0;
    }
}