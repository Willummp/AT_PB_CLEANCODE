package org.example.tp2pb.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitário para capturar screenshots durante os testes.
 * Útil para debugging e documentação de falhas.
 */
public class ScreenshotHelper {

  private static final String SCREENSHOTS_DIR = "target/screenshots";
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

  /**
   * Captura um screenshot do estado atual do navegador.
   *
   * @param driver   instância do WebDriver
   * @param testName nome do teste para identificar o screenshot
   * @return caminho do arquivo salvo, ou null se houver falha
   */
  public static String captureScreenshot(WebDriver driver, String testName) {
    try {
      // Garante que o diretório existe
      Path screenshotsPath = Paths.get(SCREENSHOTS_DIR);
      if (!Files.exists(screenshotsPath)) {
        Files.createDirectories(screenshotsPath);
      }

      // Captura o screenshot
      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

      // Cria nome do arquivo com timestamp
      String timestamp = LocalDateTime.now().format(formatter);
      String fileName = String.format("%s_%s.png", testName, timestamp);
      Path destinationPath = screenshotsPath.resolve(fileName);

      // Copia o arquivo para o destino
      Files.copy(screenshot.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

      System.out.println("Screenshot salvo em: " + destinationPath.toAbsolutePath());
      return destinationPath.toString();

    } catch (IOException e) {
      System.err.println("Erro ao capturar screenshot: " + e.getMessage());
      return null;
    }
  }

  /**
   * Captura screenshot em casode falha de teste.
   *
   * @param driver    instância do WebDriver
   * @param testName  nome do teste
   * @param throwable exceção/erro que causou a falha
   * @return caminho do screenshot ou null
   */
  public static String captureScreenshotOnFailure(WebDriver driver, String testName, Throwable throwable) {
    System.err.println("Teste falhou: " + testName);
    System.err.println("Motivo: " + throwable.getMessage());
    return captureScreenshot(driver, testName + "_FAILURE");
  }
}
