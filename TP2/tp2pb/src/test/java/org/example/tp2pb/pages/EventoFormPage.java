package org.example.tp2pb.pages;

import org.example.tp2pb.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object para a página de formulário de evento.
 * Encapsula a lógica de interação com a página form-evento.html.
 */
public class EventoFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By NOME_INPUT = By.id("nome");
    private static final By DATA_INPUT = By.id("data");
    private static final By LOCAL_INPUT = By.id("local");
    private static final By SALVAR_BUTTON = By.cssSelector("button[type='submit']");
    private static final By CANCELAR_BUTTON = By.linkText("Cancelar");
    private static final By ERRO_VALIDACAO = By.cssSelector(".invalid-feedback");
    private static final By FORM_TITLE = By.tagName("h1");

    /**
     * Construtor que inicializa o Page Object.
     *
     * @param driver instância do WebDriver
     */
    public EventoFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.DEFAULT_TIMEOUT_SECONDS));
        // Aguarda o formulário carregar
        wait.until(ExpectedConditions.visibilityOfElementLocated(FORM_TITLE));
    }

    /**
     * Preenche o formulário com os dados fornecidos.
     *
     * @param nome  nome do evento
     * @param data  data no formato yyyy-MM-dd
     * @param local local do evento
     */
    public void preencherFormulario(String nome, String data, String local) {
        if (nome != null) {
            WebElement nomeField = driver.findElement(NOME_INPUT);
            nomeField.clear();
            nomeField.sendKeys(nome);
        }

        if (data != null) {
            WebElement dataElement = driver.findElement(DATA_INPUT);
            // Usa JavaScript para definir o valor do campo de data
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].value = arguments[1]", dataElement, data);
        }

        if (local != null) {
            WebElement localField = driver.findElement(LOCAL_INPUT);
            localField.clear();
            localField.sendKeys(local);
        }
    }

    /**
     * Submete o formulário esperando sucesso (redirecionamento para lista).
     *
     * @return objeto EventoListPage
     */
    public EventoListPage submeterFormulario() {
        driver.findElement(SALVAR_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Lista de Eventos']")));
        return new EventoListPage(driver);
    }

    /**
     * Submete o formulário esperando erro de validação (permanece na mesma página).
     * Desabilita a validação HTML5 para permitir que a validação do servidor seja
     * testada.
     */
    public void submeterFormularioEsperandoErro() {
        // Desabilita validação HTML5 para testar validação do servidor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('form').setAttribute('novalidate', 'novalidate');");

        driver.findElement(SALVAR_BUTTON).click();
        // Aguarda aparecer mensagens de erro do servidor
        wait.until(ExpectedConditions.presenceOfElementLocated(ERRO_VALIDACAO));
    }

    /**
     * Verifica se há erros de validação exibidos.
     *
     * @return true se há erros, false caso contrário
     */
    public boolean temErrosDeValidacao() {
        List<WebElement> erros = driver.findElements(ERRO_VALIDACAO);
        return erros.stream().anyMatch(WebElement::isDisplayed);
    }

    /**
     * Obtém todas as mensagens de erro de validação exibidas.
     *
     * @return lista com as mensagens de erro
     */
    public List<String> getMensagensErro() {
        List<WebElement> erros = driver.findElements(ERRO_VALIDACAO);
        List<String> mensagens = new ArrayList<>();
        for (WebElement erro : erros) {
            if (erro.isDisplayed()) {
                mensagens.add(erro.getText());
            }
        }
        return mensagens;
    }

    /**
     * Verifica se um campo específico tem erro de validação.
     *
     * @param nomeCampo nome do campo (nome, data, ou local)
     * @return true se o campo tem erro, false caso contrário
     */
    public boolean temErroNoCampo(String nomeCampo) {
        By campoLocator = getCampoLocator(nomeCampo);
        WebElement campo = driver.findElement(campoLocator);
        String classes = campo.getAttribute("class");
        return classes != null && classes.contains("is-invalid");
    }

    /**
     * Obtém a mensagem de erro de um campo específico.
     *
     * @param nomeCampo nome do campo (nome, data, ou local)
     * @return mensagem de erro ou string vazia se não houver erro
     */
    public String getMensagemErroDoCampo(String nomeCampo) {
        try {
            By campoLocator = getCampoLocator(nomeCampo);
            WebElement campo = driver.findElement(campoLocator);

            // Encontra o div de erro logo após o campo
            WebElement divErro = campo
                    .findElement(By.xpath("following-sibling::div[contains(@class, 'invalid-feedback')]"));

            if (divErro.isDisplayed()) {
                return divErro.getText();
            }
        } catch (Exception e) {
            // Campo não tem erro
        }
        return "";
    }

    /**
     * Clica no botão Cancelar.
     *
     * @return objeto EventoListPage
     */
    public EventoListPage clicarCancelar() {
        driver.findElement(CANCELAR_BUTTON).click();
        return new EventoListPage(driver);
    }

    /**
     * Verifica se o formulário está em modo de edição.
     *
     * @return true se está editando, false se é novo evento
     */
    public boolean isEdicao() {
        WebElement titulo = driver.findElement(FORM_TITLE);
        return titulo.getText().contains("Editar");
    }

    /**
     * Método auxiliar para obter o locator de um campo pelo nome.
     *
     * @param nomeCampo nome do campo
     * @return By locator do campo
     */
    private By getCampoLocator(String nomeCampo) {
        return switch (nomeCampo.toLowerCase()) {
            case "nome" -> NOME_INPUT;
            case "data" -> DATA_INPUT;
            case "local" -> LOCAL_INPUT;
            default -> throw new IllegalArgumentException("Campo inválido: " + nomeCampo);
        };
    }
}