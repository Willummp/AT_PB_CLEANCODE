package com.cliente.projeto.crudpb.e2e.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Este teste SOBE a aplicação Spring Boot real em uma porta aleatória.
 * Ele testa o sistema de ponta-a-ponta (Full Stack).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroEventoE2ETest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private String baseUrl;
    private FormularioEventoPage formularioPage;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup(); // Baixa o GeckoDriver
    }

    @BeforeEach
    void setupTest() {
        org.openqa.selenium.firefox.FirefoxOptions options = new org.openqa.selenium.firefox.FirefoxOptions();
        options.addArguments("--headless");
        // options.addArguments("--no-sandbox"); // Firefox usually doesn't need this as
        // critically as Chrome
        // options.addArguments("--disable-dev-shm-usage");
        driver = new org.openqa.selenium.firefox.FirefoxDriver(options);

        baseUrl = "http://localhost:" + port;
        formularioPage = new FormularioEventoPage(driver);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Teste de Interface (Artefato 2)
     * Valida o feedback de erro seguro na UI (Artefato 4)
     */
    @Test
    void deveMostrarMensagemDeErro_QuandoSubmeterFormularioComNomeVazio() {
        driver.get(baseUrl + "/eventos/novo");

        formularioPage.preencherFormulario("", "Descrição válida"); // Nome vazio
        formularioPage.submeter();

        String mensagemErro = formularioPage.getMensagemDeErroVisivel();

        assertTrue(mensagemErro.contains("O nome é obrigatório."),
                "A mensagem de erro de nome obrigatório não foi encontrada.");
    }
}