# 🚀 Guia de Execução - TP2 Testes Automatizados

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- ✅ **JDK 17** ou superior
- ✅ **Maven 3.6+**
- ✅ **Microsoft Edge** (ou outro navegador configurado)
- ✅ **Git** (para clonar o repositório)

---

## 🔧 Configuração Inicial

### 1. Clonar o Repositório
```bash
git clone <url-do-repositorio>
cd tp2pb
```

### 2. Verificar Instalação do Java
```bash
java -version
```
Deve retornar **Java 17** ou superior.

### 3. Verificar Instalação do Maven
```bash
mvn -version
```

---

## ▶️ Executando a Aplicação

### Iniciar o Servidor
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080/eventos**

### Acessar a Interface Web
1. Abra o navegador
2. Acesse: `http://localhost:8080/eventos`
3. Use a interface para:
   - ➕ Adicionar novos eventos
   - ✏️ Editar eventos existentes
   - 🗑️ Excluir eventos
   - 📋 Visualizar lista de eventos

---

## 🧪 Executando os Testes

### Executar Todos os Testes
```bash
mvn clean test
```

### Executar Testes e Gerar Relatório de Cobertura
```bash
mvn clean verify
```

O relatório JaCoCo estará em: `target/site/jacoco/index.html`

### Executar Suite Específica
```bash
# Apenas testes CRUD
mvn test -Dtest=EventoCrudTest

# Apenas testes de validação
mvn test -Dtest=EventoValidationTest

# Apenas testes parametrizados
mvn test -Dtest=EventoParameterizedTest
```

---

## 📊 Visualizando Relatórios

### Relatório de Cobertura JaCoCo
1. Execute: `mvn clean verify`
2. Abra: `target/site/jacoco/index.html` no navegador
3. Navegue pelos pacotes para ver cobertura detalhada

### Screenshots de Falhas
- Screenshots automáticos são salvos em: `target/screenshots/`
- Nomeados com timestamp para fácil identificação

### Relatórios Surefire
- Localização: `target/surefire-reports/`
- Formato: TXT e XML

---

## 🎯 Acessando o Console H2

Para visualizar o banco de dados em memória:

1. Inicie a aplicação: `mvn spring-boot:run`
2. Acesse: `http://localhost:8080/h2-console`
3. Configure:
   - **JDBC URL**: `jdbc:h2:mem:eventodb`
   - **Username**: `sa`
   - **Password**: _(deixe vazio)_
4. Clique em **Connect**

---

## 🔄 Workflow Completo

### Desenvolvimento e Teste
```bash
# 1. Limpar builds anteriores
mvn clean

# 2. Compilar o projeto
mvn compile

# 3. Executar testes
mvn test

# 4. Verificar cobertura (executará testes novamente)
mvn verify

# 5. Iniciar aplicação
mvn spring-boot:run
```

### Apenas Executar Testes Rapidamente
```bash
mvn clean test
```

---

## 🛠️ Configuração do WebDriver

### Usando Edge (Padrão)
O projeto está configurado para usar **Microsoft Edge**.

Driver localizado em: `driver/msedgedriver.exe`

### Trocar para Chrome
Edite as classes de teste e altere:

```java
// Remova:
System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
driver = new EdgeDriver();

// Adicione:
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver();
```

---

## 🐛 Troubleshooting

### Erro: "Driver executable does not exist"
**Solução**: Certifique-se que `driver/msedgedriver.exe` existe ou use WebDriverManager:
```java
WebDriverManager.edgedriver().setup();
driver = new EdgeDriver();
```

### Testes falhando aleatoriamente
**Solução**: Aumente o timeout em [`TestConfig.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/config/TestConfig.java):
```java
public static final int DEFAULT_TIMEOUT_SECONDS = 15; // Era 10
```

### Porta 8080 já em uso
**Solução**: Altere a porta em `application.properties`:
```properties
server.port=8081
```

**E atualize** `TestConfig.java`:
```java
public static final String BASE_URL = "http://localhost:8081/eventos";
```

### Build Maven falhando
**Solução**:
```bash
mvn clean
mvn install -DskipTests
mvn test
```

### Erro de encoding
**Solução**: Certifique-se que `application.properties` tem:
```properties
spring.messages.encoding=UTF-8
server.servlet.encoding.charset=UTF-8
```

---

## 📱 Atalhos Úteis

| Comando | Descrição |
|---------|-----------|
| `mvn spring-boot:run` | Inicia aplicação |
| `mvn clean test` | Executa testes |
| `mvn clean verify` | Testes + cobertura |
| `mvn clean` | Limpa build anterior |
| `Ctrl + C` | Para servidor/testes |

---

## ✅ Checklist Pré-Entrega

Antes de enviar o trabalho, verifique:

- [ ] Todos os testes passam: `mvn clean test`
- [ ] Cobertura ≥ 80%: `mvn verify`
- [ ] Aplicação inicia: `mvn spring-boot:run`
- [ ] Interface web funciona em `http://localhost:8080/eventos`
- [ ] Screenshots de falhas funcionando
- [ ] Código commitado no Git
- [ ] README.md atualizado

---

## 📞 Informações Adicionais

- **Framework**: Spring Boot 3.5.6
- **Java**: 17
- **Testes**: JUnit 5.10.2 + Selenium 4.21.0
- **Cobertura**: JaCoCo 0.8.11
- **Banco de Dados**: H2 (in-memory)

**Desenvolvido por**: Lucas Ferreira
**Data**: Novembro/2025

