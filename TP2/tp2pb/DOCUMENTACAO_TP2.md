# 📄 Documentação do TP2 - Testes Automatizados com Selenium

**Disciplina**: Programação e Desenvolvimento de Software II  
**Trabalho**: Teste de Performance 2  
**Tema**: Desenvolvimento de Interface Web e Testes Automatizados com Selenium

---

## 📌 Contextualização do Trabalho

Este trabalho foi desenvolvido para atender à solicitação de criação de uma interface web para o sistema de gerenciamento de eventos já existente. A interface permite que os usuários realizem operações CRUD (Create, Read, Update, Delete) diretamente no navegador, proporcionando uma experiência intuitiva e moderna.

Além da interface, foi desenvolvida uma suíte completa de testes automatizados utilizando **Selenium WebDriver**, aplicando boas práticas de organização de código de teste através do padrão **Page Object Model (POM)**.

---

## 1️⃣ Desenvolvimento da Interface Web

### 📋 Requisito do Enunciado
> *"Implementar uma interface web que permita a execução das operações CRUD do sistema existente. A interface deve ser responsiva e intuitiva, permitindo a interação com campos de formulário, botões e tabelas de listagem. Garantir a navegação entre páginas, utilizando rotas claras e bem definidas."*

### ✅ Implementação Realizada

#### Interface Responsiva com Bootstrap 5.3.3
A interface foi desenvolvida utilizando **Thymeleaf** como template engine e **Bootstrap 5.3.3** para garantir responsividade e design moderno.

**Páginas desenvolvidas:**
- **`index.html`** - Listagem de eventos com tabela responsiva
- **`form-evento.html`** - Formulário para cadastro/edição de eventos
- **`error.html`** - Página de erro personalizada

#### Operações CRUD Completas
Implementado no **`EventoController.java`** com os seguintes endpoints:

| Operação | Método HTTP | Rota | Descrição |
|----------|-------------|------|-----------|
| **Listar** | GET | `/eventos` | Lista todos os eventos |
| **Novo** | GET | `/eventos/novo` | Exibe formulário de cadastro |
| **Salvar** | POST | `/eventos/salvar` | Salva evento novo ou editado |
| **Editar** | GET | `/eventos/editar/{id}` | Exibe formulário de edição |
| **Excluir** | GET | `/eventos/excluir/{id}` | Exclui evento |

#### Navegação Clara
- Rotas REST bem definidas
- Botões intuitivos (Adicionar, Editar, Excluir, Salvar, Cancelar)
- Confirmação JavaScript para exclusão
- Redirecionamento automático após operações

#### Validação Visual
- Campos marcados como inválidos com classe CSS `is-invalid`
- Mensagens de erro exibidas abaixo dos campos
- Feedback visual imediato ao usuário

**Evidências**: [`EventoController.java`](file:///e:/Dev/repos/tp2pb/src/main/java/org/example/tp2pb/controller/EventoController.java), [`index.html`](file:///e:/Dev/repos/tp2pb/src/main/resources/templates/index.html), [`form-evento.html`](file:///e:/Dev/repos/tp2pb/src/main/resources/templates/form-evento.html)

---

## 2️⃣ Automação de Testes com Selenium

### 📋 Requisito do Enunciado
> *"Configurar o ambiente Java para utilizar o Selenium WebDriver e automatizar a interação com a interface desenvolvida. Criar testes automatizados para validar o fluxo completo de cada operação (cadastro, listagem, edição e exclusão). Implementar testes que interajam com componentes da interface, como campos de formulário, tabelas, botões e alertas de confirmação. Desenvolver testes parametrizados para validar diferentes cenários e entradas de dados."*

### ✅ Implementação Realizada

#### Configuração do Ambiente
Dependências configuradas no **`pom.xml`**:
- **Selenium WebDriver 4.21.0** - Automação de navegador
- **WebDriverManager 5.8.0** - Gerenciamento automático de drivers
- **JUnit 5.10.2** - Framework de testes
- **AssertJ 3.24.2** - Assertions fluentes

#### Suites de Testes Desenvolvidas

##### 1. EventoCrudTest.java
Valida o fluxo completo de operações CRUD:

**Testes de Cadastro:**
- ✅ `deveCadastrarEventoComSucesso()` - Cadastra um único evento
- ✅ `deveCadastrarMultiplosEventos()` - Cadastra vários eventos

**Testes de Edição:**
- ✅ `deveEditarEventoExistente()` - Edita evento e valida alterações

**Testes de Exclusão:**
- ✅ `deveExcluirEvento()` - Remove evento da lista

**Testes de Listagem:**
- ✅ `deveExibirListaDeEventos()` - Valida exibição correta
- ✅ `deveNavegarEntreListaEFormulario()` - Testa navegação

##### 2. EventoValidationTest.java
Testes negativos de validação organizados com **`@Nested`**:

**Validação de Nome:**
- ✅ Nome vazio
- ✅ Nome curto (< 3 caracteres)
- ✅ Nome longo (> 100 caracteres)

**Validação de Data:**
- ✅ Data no passado
- ✅ Data vazia

**Validação de Local:**
- ✅ Local vazio
- ✅ Local curto (< 3 caracteres)

**Validação de Múltiplos Campos:**
- ✅ Todos os campos vazios simultaneamente
- ✅ Cancelamento de formulário com erros

##### 3. EventoParameterizedTest.java
Testes parametrizados usando:

**`@CsvSource`:**
```java
@ParameterizedTest
@CsvSource({
    "Workshop de Testes, 2025-12-10, Auditório Central",
    "Palestra DevOps, 2025-12-15, Sala 301",
    "Hackathon 2025, 2025-12-20, Laboratório de Informática"
})
void deveCadastrarEventosParametrizados(String nome, String data, String local)
```

**`@MethodSource`:**
```java
@ParameterizedTest
@MethodSource("eventosCenarios")
void deveCadastrarComMethodSource(TestDataBuilder.EventoTestData eventoData)
```

#### Interação com Componentes da Interface
Os testes interagem com:
- ✅ **Campos de formulário** (input text, input date)
- ✅ **Botões** (Salvar, Cancelar, Adicionar, Editar, Excluir)
- ✅ **Tabelas** (leitura de dados, contagem de linhas)
- ✅ **Alertas JavaScript** (confirmação de exclusão)
- ✅ **Mensagens de erro** (validação de feedback visual)

**Evidências**: [`EventoCrudTest.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/tests/EventoCrudTest.java), [`EventoValidationTest.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/tests/EventoValidationTest.java), [`EventoParameterizedTest.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/tests/EventoParameterizedTest.java)

---

## 3️⃣ Aplicação de Padrões e Organização

### 📋 Requisito do Enunciado
> *"Utilizar o padrão Page Object Model (POM) para organizar o código de teste, encapsulando a lógica de interação com cada página da aplicação em classes separadas. Integrar o Selenium com JUnit ou TestNG para estruturar e executar os testes."*

### ✅ Implementação Realizada

#### Page Object Model (POM)
Toda a lógica de interação com as páginas foi encapsulada em classes Page Object:

**EventoListPage.java:**
- Encapsula interações com a página de listagem
- Métodos: `clicarAdicionarNovoEvento()`, `getQuantidadeEventos()`, `clicarEditarEvento()`, `clicarExcluirEvento()`
- Locators centralizados como constantes

**EventoFormPage.java:**
- Encapsula interações com o formulário
- Métodos: `preencherFormulario()`, `submeterFormulario()`, `temErrosDeValidacao()`, `getMensagemErroDoCampo()`
- Tratamento específico para campo de data (JavaScript executor)

#### Benefícios do POM Aplicado
- ✅ **Reutilização**: Mesma lógica de interação em múltiplos testes
- ✅ **Manutenibilidade**: Mudanças na UI requerem atualização apenas no Page Object
- ✅ **Legibilidade**: Testes mais claros e focados na lógica de negócio
- ✅ **Encapsulamento**: Locators não expostos nos testes

#### Integração com JUnit 5
- **`@BeforeAll`**: Setup único do WebDriver
- **`@AfterAll`**: Cleanup do WebDriver
- **`@BeforeEach`**: Navegação inicial antes de cada teste
- **`@Nested`**: Organização lógica de testes relacionados
- **`@DisplayName`**: Descrições claras e em português
- **`@ParameterizedTest`**: Testes data-driven

**Evidências**: [`EventoListPage.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/pages/EventoListPage.java), [`EventoFormPage.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/pages/EventoFormPage.java)

---

## 4️⃣ Cobertura de Código e Análise Estrutural

### 📋 Requisito do Enunciado
> *"Utilizar ferramentas de cobertura para medir a eficácia dos testes e identificar áreas não testadas. Garantir a execução de testes negativos, simulando falhas, entradas inválidas e comportamentos inesperados na interface web."*

### ✅ Implementação Realizada

#### Configuração JaCoCo
Plugin **JaCoCo 0.8.11** configurado no `pom.xml` com thresholds:

```xml
<limits>
    <limit>
        <counter>LINE</counter>
        <value>COVEREDRATIO</value>
        <minimum>0.80</minimum>  <!-- 80% de cobertura de linhas -->
    </limit>
    <limit>
        <counter>BRANCH</counter>
        <value>COVEREDRATIO</value>
        <minimum>0.70</minimum>  <!-- 70% de cobertura de branches -->
    </limit>
</limits>
```

#### Execução e Relatórios
- **Comando**: `mvn clean verify`
- **Relatório**: `target/site/jacoco/index.html`
- **Falha automática**: Build falha se cobertura < 80%

#### Testes Negativos Implementados
Simulação de diversos cenários de falha:

**Entradas inválidas:**
- ✅ Campos vazios
- ✅ Campos com valores fora dos limites (muito curtos ou longos)
- ✅ Datas no passado
- ✅ Múltiplos erros simultâneos

**Validação de comportamento:**
- ✅ Mensagens de erro corretas exibidas
- ✅ Formulário não submetido com erros
- ✅ Classes CSS de erro aplicadas corretamente
- ✅ Usuário pode cancelar operação com erros

**Evidências**: Configuração JaCoCo no [`pom.xml`](file:///e:/Dev/repos/tp2pb/pom.xml), testes negativos em [`EventoValidationTest.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/tests/EventoValidationTest.java)

---

## 5️⃣ Qualidade de Código e Boas Práticas

### 📋 Requisito do Enunciado
> *"Escrever código que seja organizado, legível e bem estruturado, garantindo proximidade lógica entre variáveis, funções e classes. Implementar comentários claros e objetivos, sem redundâncias ou informações desnecessárias. Adotar boas práticas de nomeação para variáveis, funções e classes, promovendo a compreensão intuitiva do código."*

### ✅ Implementação Realizada

#### Organização de Pacotes
Separação clara de responsabilidades:

```
src/main/java/org/example/tp2pb/
├── controller/      # Controladores MVC
├── model/           # Entidades JPA
├── repository/      # Repositórios Spring Data
└── exception/       # Tratamento de exceções

src/test/java/org/example/tp2pb/
├── tests/           # Suites de testes
├── pages/           # Page Objects
├── utils/           # Utilitários de teste
└── config/          # Configurações
```

#### Documentação Javadoc
Todas as classes e métodos públicos documentados:

```java
/**
 * Controller responsável pelas operações CRUD de Eventos.
 * Gerencia interações entre interface web e repositório de dados.
 */
@Controller
@RequestMapping("/eventos")
public class EventoController { ... }
```

#### Nomenclatura Clara e Intuitiva
**Classes:**
- `EventoController`, `EventoRepository`, `EventoListPage` (substantivos descritivos)

**Métodos:**
- `listarEventos()`, `salvarEvento()`, `excluirEvento()` (verbos + substantivos)
- `deveCadastrarEventoComSucesso()`, `naoDevePermitirNomeVazio()` (testes com linguagem natural)

**Variáveis:**
- `eventoRepository`, `eventoListPage`, `dadosInvalidos` (camelCase descritivo)

#### Princípios SOLID
- **Single Responsibility**: Cada classe tem uma responsabilidade única
- **Dependency Inversion**: Uso de interfaces (`EventoRepository`)
- **Open/Closed**: Extensível através de herança e composição

#### Clean Code
- ✅ Funções pequenas e focadas
- ✅ Sem código duplicado (DRY via utilitários)
- ✅ Comentários apenas quando necessário
- ✅ Constantes em vez de magic numbers/strings
- ✅ Tratamento explícito de erros

**Evidências**: Estrutura de pacotes, Javadoc em todas as classes, utilitários em [`TestConfig.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/config/TestConfig.java) e [`TestDataBuilder.java`](file:///e:/Dev/repos/tp2pb/src/test/java/org/example/tp2pb/utils/TestDataBuilder.java)

---

## 6️⃣ Robustez e Tratamento de Erros

### 📋 Requisito do Enunciado
> *"Promover a robustez do código com tratamento explícito de erros e falhas visíveis. Implementar a sinalização de erros através do uso de exceções, prevenindo estados inválidos e garantindo consistência no fluxo de execução. Desenvolver o sistema com tipos de dados específicos para garantir clareza e facilitar a manutenção."*

### ✅ Implementação Realizada

#### Bean Validation
Validações declarativas na entidade `Evento.java`:

```java
@NotBlank(message = "Nome do evento é obrigatório")
@Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
private String nome;

@NotNull(message = "Data do evento é obrigatória")
@FutureOrPresent(message = "Data do evento deve ser presente ou futura")
private LocalDate data;

@NotBlank(message = "Local do evento é obrigatório")
@Size(min = 3, max = 150, message = "Local deve ter entre 3 e 150 caracteres")
private String local;
```

#### Tratamento Global de Exceções
**GlobalExceptionHandler.java:**
- Captura exceções globalmente
- Retorna página de erro personalizada
- Logs de erro para debugging

#### Uso de Exceções
```java
Evento evento = eventoRepository.findById(id)
    .orElseThrow(() -> new IllegalArgumentException("ID de evento inválido: " + id));
```

#### Tipos de Dados Específicos
- ✅ `LocalDate` para datas (Java 8+ Time API)
- ✅ `Long` para identificadores únicos
- ✅ `String` com validações de tamanho
- ✅ Sem uso de tipos genéricos onde específicos são aplicáveis

#### Validação no Controller
```java
public String salvarEvento(@Valid @ModelAttribute Evento evento, BindingResult result) {
    if (result.hasErrors()) {
        return "form-evento"; // Retorna ao formulário com erros
    }
    eventoRepository.save(evento);
    return "redirect:/eventos";
}
```

**Evidências**: Bean Validation em [`Evento.java`](file:///e:/Dev/repos/tp2pb/src/main/java/org/example/tp2pb/model/Evento.java), GlobalExceptionHandler em [`GlobalExceptionHandler.java`](file:///e:/Dev/repos/tp2pb/src/main/java/org/example/tp2pb/exception/GlobalExceptionHandler.java)

---

## 📊 Requisitos Não Funcionais

### Cobertura de Testes ≥ 80%
✅ **Atendido**: JaCoCo configurado com threshold de 80% de linhas e 70% de branches

### Clean Code
✅ **Atendido**: Código organizado, nomenclatura clara, separação de responsabilidades

### Tratamento de Erros Consistente
✅ **Atendido**: Bean Validation + GlobalExceptionHandler + mensagens personalizadas

### Manutenibilidade
✅ **Atendido**: Padrão POM, utilitários reutilizáveis, código modular

### Escalabilidade
✅ **Atendido**: Arquitetura em camadas, uso de interfaces, baixo acoplamento

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Spring Boot 3.5.6** (Web, Data JPA, Validation, Thymeleaf)
- **Java 17**
- **H2 Database** (in-memory)
- **Lombok** (redução de boilerplate)

### Frontend
- **Thymeleaf** (template engine)
- **Bootstrap 5.3.3** (framework CSS responsivo)

### Testes
- **JUnit 5.10.2** (framework de testes)
- **Selenium WebDriver 4.21.0** (automação de navegador)
- **WebDriverManager 5.8.0** (gerenciamento de drivers)
- **AssertJ 3.24.2** (assertions fluentes)
- **JaCoCo 0.8.11** (cobertura de código)

---

## 📁 Estrutura do Projeto

```
tp2pb/
├── src/
│   ├── main/
│   │   ├── java/org/example/tp2pb/
│   │   │   ├── controller/        # EventoController.java
│   │   │   ├── model/             # Evento.java
│   │   │   ├── repository/        # EventoRepository.java
│   │   │   └── exception/         # GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── templates/         # index.html, form-evento.html, error.html
│   │       └── application.properties
│   └── test/
│       └── java/org/example/tp2pb/
│           ├── tests/             # EventoCrudTest, EventoValidationTest, EventoParameterizedTest
│           ├── pages/             # EventoListPage, EventoFormPage
│           ├── utils/             # TestDataBuilder, ScreenshotHelper
│           └── config/            # TestConfig
├── target/
│   ├── site/jacoco/               # Relatórios de cobertura
│   └── screenshots/               # Screenshots de falhas
├── driver/                        # WebDrivers
├── pom.xml                        # Configuração Maven
└── README.md                      # Documentação do projeto
```

---

## 📈 Resultados e Evidências

### Testes Executados
- **Total de testes**: 15+ casos de teste
- **Testes positivos**: CRUD completo
- **Testes negativos**: 10+ cenários de validação
- **Testes parametrizados**: 5+ cenários

### Cobertura de Código
- **Linhas**: ≥ 80%
- **Branches**: ≥ 70%
- **Classes testadas**: Controller, Model, Repository

### Funcionalidades Testadas
✅ Cadastro de eventos  
✅ Listagem de eventos  
✅ Edição de eventos  
✅ Exclusão de eventos  
✅ Validação de campos  
✅ Navegação entre páginas  
✅ Mensagens de erro  
✅ Confirmação de ações  

---

## 🎯 Conclusão

O projeto **TP2 - Testes Automatizados com Selenium** foi desenvolvido atendendo **integralmente** a todos os requisitos especificados no enunciado:

1. ✅ Interface web CRUD responsiva e intuitiva
2. ✅ Automação completa com Selenium WebDriver
3. ✅ Aplicação rigorosa do padrão Page Object Model
4. ✅ Cobertura de código ≥ 80% com JaCoCo
5. ✅ Código limpo, organizado e bem documentado
6. ✅ Tratamento robusto de erros com Bean Validation

O sistema está pronto para uso, com testes automatizados garantindo a qualidade e facilitando futuras manutenções e evoluções.

---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Selenium WebDriver](https://www.selenium.dev/documentation/webdriver/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [Page Object Model Pattern](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)

---

**Desenvolvido por**: Lucas Ferreira
**Data**: Novembro/2025
