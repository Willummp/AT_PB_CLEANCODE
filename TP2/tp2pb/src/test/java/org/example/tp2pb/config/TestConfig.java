package org.example.tp2pb.config;

/**
 * Configurações centralizadas para os testes.
 * Evita magic numbers e strings hardcoded espalhad os pelos testes.
 */
public class TestConfig {

  // URLs
  public static final String BASE_URL = "http://localhost:8080/eventos";
  public static final String NOVO_EVENTO_URL = BASE_URL + "/novo";

  // Timeouts
  public static final int DEFAULT_TIMEOUT_SECONDS = 10;
  public static final int SCREENSHOT_TIMEOUT_MS = 500;
  public static final int PAGE_LOAD_TIMEOUT_SECONDS = 15;

  // Diretórios
  public static final String SCREENSHOTS_DIR = "target/screenshots";

  // Dados de teste padrão
  public static final String NOME_EVENTO_PADRAO = "Conferência de Testes Automatizados";
  public static final String DATA_EVENTO_PADRAO = "2025-12-01";
  public static final String LOCAL_EVENTO_PADRAO = "Centro de Convenções Online";

  // Mensagens de erro esperadas
  public static final String ERRO_NOME_OBRIGATORIO = "Nome do evento é obrigatório";
  public static final String ERRO_DATA_OBRIGATORIA = "Data do evento é obrigatória";
  public static final String ERRO_LOCAL_OBRIGATORIO = "Local do evento é obrigatório";
  public static final String ERRO_NOME_MIN_LENGTH = "Nome deve ter entre 3 e 100 caracteres";
  public static final String ERRO_DATA_PASSADA = "Data do evento deve ser presente ou futura";

  private TestConfig() {
    // Classe utilitária, não deve ser instanciada
  }
}
