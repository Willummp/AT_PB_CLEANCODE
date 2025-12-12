package org.example.tp2pb.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Classe base para testes Selenium com Spring Boot Test.
 * Inicia automaticamente a aplicação Spring Boot na porta 8080 antes dos
 * testes.
 * 
 * Benefícios:
 * - Elimina necessidade de rodar aplicação manualmente
 * - Garante ambiente consistente para testes end-to-end
 * - Usa banco de dados H2 em memória limpo para cada classe de teste
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseSeleniumTest {

  protected static WebDriver driver;

  @BeforeAll
  public static void setupClass() {
    WebDriverManager.firefoxdriver().setup();
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("--headless");
    driver = new FirefoxDriver(options);
  }

  @AfterAll
  public static void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
