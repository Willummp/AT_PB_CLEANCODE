# Assessment PB (Projeto de Bloco) - Entrega Final

Este repositório contém a entrega final do Assessment de Projeto de Bloco. A estrutura está dividida por Testes de Performance (TPs), cada um contendo seu respectivo código e documentação.

## Guia Rápido de Execução

Para auditar ou testar os projetos, utilize os seguintes comandos no terminal (certifique-se de ter o Maven e JDK 17+ instalados):

### TP1 (Testes Unitários)
```bash
cd TP1/crud-java-tp1
mvn test
```

### TP2 (Testes E2E com Selenium)
```bash
cd TP2/tp2pb
mvn test
```

### TP3 (API e Testes)
```bash
cd TP3/TP3_CODIGO/com-cliente-projeto
mvn test
# Para rodar a aplicação: mvn spring-boot:run
```

### TP4 (Testes de Integração/Performance)
```bash
cd TP4/TP4/com-cliente-projeto
mvn test
```

### TP5 (Deploy/Testes)
```bash
cd TP5/com-cliente-projeto
mvn test
```

### Assessment (AT)
```bash
cd AT/com-cliente-projeto
mvn test
```

---

## Localização dos PDFs

Os documentos PDF com os relatórios e evidências de cada entrega estão localizados nas seguintes pastas:

*   **TP1**: `TP1/tp1_entrega.pdf`
*   **TP2**: `TP2/tp2_pb.pdf`
*   **TP3**: `TP3/tp3_pb.pdf`
*   **TP4**: `TP4/tp4_pb.pdf`
*   **TP5**: `TP5/lucas_ferreira_tp5_pb.pdf`

---

## Instruções Gerais de Execução

Cada diretório de TP contém um projeto específico (geralmente Java/Maven) com suas instruções detalhadas. No entanto, para projetos que envolvem automação de UI (como o **TP2**), há requisitos importantes sobre o driver do navegador.

### Configuração do WebDriver (Selenium)

Os testes automatizados de interface (E2E) foram configurados utilizando o **WebDriverManager** e, por padrão, executam utilizando o navegador **Firefox**.

*   **Se você possui o Firefox instalado**: Os testes devem rodar automaticamente sem configuração adicional, pois o WebDriverManager baixará o `geckodriver` compatível.
*   **Se você deseja usar o Chrome ou outro navegador**: É necessário alterar manualmente a configuração no código.

**Como alterar para o Chrome (Exemplo no TP2):**

1.  Navegue até a classe base de teste (ex: `TP2/tp2pb/src/test/java/org/example/tp2pb/tests/BaseSeleniumTest.java`).
2.  Localize o método `@BeforeAll` ou `setupClass`.
3.  Altere a configuração do driver:

```java
// De (Firefox padrão):
WebDriverManager.firefoxdriver().setup();
driver = new FirefoxDriver(options);

// Para (Chrome):
WebDriverManager.chromedriver().setup();
ChromeOptions options = new ChromeOptions();
// Adicione argumentos se necessário, ex: options.addArguments("--headless");
driver = new ChromeDriver(options);
```

Certifique-se de que o navegador escolhido esteja instalado no ambiente de execução.
