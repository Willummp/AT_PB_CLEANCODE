# Integração Spring Boot Test + Selenium - Walkthrough

Documentação completa da implementação da integração Spring Boot Test com testes Selenium e correção dos testes de validação.

## 🎯 Objetivo Alcançado

Resolver o erro `ERR_CONNECTION_REFUSED` que ocorria quando executava `mvn clean test`, configurando os testes Selenium para iniciar automaticamente a aplicação Spring Boot.

## ✅ Resultado Final

```
Tests run: 33, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
Total time: 01:36 min
```

**100% dos testes passando!**

---

## 📋 Mudanças Implementadas

### 1. Configuração Spring Boot Test

#### [NEW] `application-test.properties`
```properties
# Define porta fixa para testes
server.port=8080

# Banco H2 em memória isolado
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop

# Reduz logging
logging.level.root=WARN
```

#### [NEW] `BaseSeleniumTest.java`
Classe base abstrata com anotações Spring Boot Test:
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseSeleniumTest {
    protected static WebDriver driver;
}
```

**Benefícios**:
- ✅ Inicia aplicação automaticamente na porta 8080
- ✅ Usa perfil "test" com configurações isoladas
- ✅ Banco de dados limpo para cada classe de teste
- ✅ Elimina necessidade de rodar aplicação manualmente

---

### 2. Atualização dos Testes Selenium

Modificadas 3 classes de teste para estender `BaseSeleniumTest`:

- ✅ `EventoCrudTest extends BaseSeleniumTest`
- ✅ `EventoValidationTest extends BaseSeleniumTest`
- ✅ `EventoParameterizedTest extends BaseSeleniumTest`

**Antes**:
```java
public class EventoCrudTest {
    private static WebDriver driver;
    
    @BeforeAll
    public static void setupClass() {
        driver = new EdgeDriver();
    }
}
```

**Depois**:
```java
public class EventoCrudTest extends BaseSeleniumTest {
    private EventoListPage eventoListPage;
    // WebDriver herdado da classe base
}
```

---

### 3. Correção dos Testes de Validação

#### Problema Identificado
Validação HTML5 (atributo `required`) bloqueava formulário antes de chegar ao servidor, impedindo Bean Validation de executar.

#### Solução Implementada

Modificado `EventoFormPage.submeterFormularioEsperandoErro()`:

```java
public void submeterFormularioEsperandoErro() {
    // Desabilita validação HTML5 para testar validação do servidor
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("document.querySelector('form').setAttribute('novalidate', 'novalidate');");
    
    driver.findElement(SALVAR_BUTTON).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(ERRO_VALIDACAO));
}
```

**Efeito**: Dados inválidos agora chegam ao servidor → Bean Validation processa → mensagens `.invalid-feedback` aparecem → testes passam.

---

### 4. Atualização de Dependências

#### `pom.xml`
```diff
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
-   <version>4.21.0</version>
+   <version>4.31.0</version>
    <scope>test</scope>
</dependency>
```

**Resolve**: Warnings de CDP (Chrome DevTools Protocol) para Edge 142.

---

## 📊 Testes Executados e Resultados

### EventoCrudTest - 8 testes ✅
- **Cadastro** (2 testes)
  - ✅ Cadastrar novo evento
  - ✅ Cadastrar múltiplos eventos
- **Edição** (2 testes)
  - ✅ Criar evento para edição
  - ✅ Editar evento existente
- **Exclusão** (2 testes)
  - ✅ Criar evento para exclusão
  - ✅ Excluir evento existente
- **Listagem** (2 testes)
  - ✅ Exibir todos eventos
  - ✅ Navegar para formulário e voltar

### EventoParameterizedTest - 16 testes ✅
- **Cadastro parametrizado** (5 testes)
  - ✅ 5 eventos válidos via @CsvSource
- **Validação nome** (3 testes)
  - ✅ Rejeitar nome vazio
  - ✅ Rejeitar "AB" (muito curto)
  - ✅ Rejeitar "A" (muito curto)
- **Validação local** (3 testes)
  - ✅ Rejeitar local vazio
  - ✅ Rejeitar "AB" (muito curto)
  - ✅ Rejeitar "X" (muito curto)
- **Cadastro via @MethodSource** (5 testes)
  - ✅ 5 eventos com datas dinâmicas

### EventoValidationTest - 9 testes ✅
- **Validação Nome** (3 testes)
  - ✅ Nome vazio rejeitado
  - ✅ Nome curto (<3 chars)
  - ✅ Nome longo (>100 chars)
- **Validação Data** (2 testes)
  - ✅ Data passada rejeitada
  - ✅ Data vazia rejeitada
- **Validação Local** (2 testes)
  - ✅ Local vazio rejeitado
  - ✅ Local curto (<3 chars)
- **Múltiplos Campos** (2 testes)
  - ✅ Todos campos vazios
  - ✅ Cancelar formulário

---

## 🔧 Comandos de Verificação

### Executar testes específicos
```bash
# Apenas testes CRUD
mvn test -Dtest=EventoCrudTest

# Apenas validações
mvn test -Dtest=EventoValidationTest

# Apenas parametrizados
mvn test -Dtest=EventoParameterizedTest
```

### Executar todos os testes
```bash
mvn clean test
```

### Visualizar relatório JaCoCo
```bash
# Após executar testes, abrir:
target/site/jacoco/index.html
```

---

## 🎓 Lições Aprendidas

### 1. Spring Boot Test com Selenium
- `@SpringBootTest` inicia a aplicação automaticamente
- `DEFINED_PORT` garante porta fixa (8080)
- `@ActiveProfiles("test")` usa configurações isoladas
- `@DirtiesContext` limpa contexto após cada classe

### 2. Validação HTML5 vs Bean Validation
- Validação HTML5 acontece no **cliente** (navegador)
- Bean Validation acontece no **servidor** (Spring)
- Atributo `novalidate` desabilita validação HTML5
- Isso permite testar validação do servidor isoladamente

### 3. Page Object Pattern
- Encapsula lógica de interação com páginas
- Métodos específicos para cenários de sucesso vs erro
- JavaScript Executor útil para casos especiais
- Waits explícitos melhoram confiabilidade

---

## 📁 Estrutura de Arquivos Modificados

```
e:/Dev/repos/tp2pb/
├── pom.xml (Selenium 4.21.0 → 4.31.0)
├── src/
│   └── test/
│       ├── resources/
│       │   └── application-test.properties (NEW)
│       └── java/org/example/tp2pb/
│           ├── tests/
│           │   ├── BaseSeleniumTest.java (NEW)
│           │   ├── EventoCrudTest.java (MODIFIED)
│           │   ├── EventoValidationTest.java (MODIFIED)
│           │   └── EventoParameterizedTest.java (MODIFIED)
│           └── pages/
│               └── EventoFormPage.java (MODIFIED)
```

---

## ✨ Próximos Passos Sugeridos

1. **Cobertura de Código**: Revisar relatório JaCoCo e aumentar cobertura se necessário
2. **CI/CD**: Integrar testes em pipeline (GitHub Actions, Jenkins, etc.)
3. **Testes Negativos**: Expandir cenários de erro (caracteres especiais, SQL injection, etc.)
4. **Performance**: Considerar testes de carga com JMeter/Gatling
5. **Documentação**: Atualizar README.md com instruções de execução

---

## 🏆 Conclusão

A integração Spring Boot Test + Selenium foi implementada com sucesso, resolvendo:
- ❌ `ERR_CONNECTION_REFUSED` → ✅ Aplicação inicia automaticamente
- ❌ 6 testes de validação falhando → ✅ Todos passando
- ❌ Setup manual necessário → ✅ Totalmente automatizado

**Resultado**: Suite de testes 100% funcional e pronta para integração contínua!
