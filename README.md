# 📚 Entrega Geral - Projeto de Bloco de Engenharia Disciplinada de Softwares
**Aluno:** Lucas Ferreira
**Curso:** Engenharia de Software

> [!IMPORTANT]
> **Configuração de CI/CD (GitHub Actions)**
> 
> Este repositório opera como um **Monorepo**, contendo múltiplos projetos (TP1 a TP5 e AT).
> Para garantir que os pipelines de integração contínua (CI) funcionem corretamente neste formato unificado, as configurações de workflow foram centralizadas na pasta raiz `.github/workflows`.
> 
> **Como funciona:**
> - Cada projeto (TP4, TP5, AT) tem seu próprio arquivo YAML dedicado (ex: `tp5-ci.yml`).
> - Os workflows utilizam filtros de caminho (`paths`), ou seja, o pipeline do TP5 só é disparado quando há alterações dentro da pasta `TP5/**`.
> - Os jobs estão configurados com `working-directory` para executar os comandos Maven dentro da subpasta correta de cada projeto.
>
> **Status:** ✅ Configurado e Pronto para Uso no GitHub.

---

## 🛠️ Pré-requisitos de Sistema

Antes de executar, certifique-se de que seu ambiente possui:

1.  **Java JDK 17** ou superior.
2.  **Apache Maven** (3.8+).
3.  **Navegadores Instalados** (para testes E2E):
    *   **Google Chrome** (Necessário para TP5 e AT).
    *   **Mozilla Firefox** (Necessário para TP2).
    *   *Nota: Os drivers (chromedriver/geckodriver) são baixados automaticamente, mas os navegadores precisam estar instalados no S.O.*

---

## ⚡ Guia Rápido de Execução

Para auditar ou testar os projetos, utilize os seguintes comandos no terminal:

### 🚀 Auditoria Completa Automática
Para rodar todos os testes de todos os projetos em sequência, execute o script na raiz:
```bash
bash run_all_tests.sh
```
> Este script valida **TP1, TP2, TP3, TP4, TP5 e AT** de uma só vez.

---

### Execução Individual por Projeto

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

## � Relatórios de Cobertura (JaCoCo)

O projeto utiliza o plugin **JaCoCo** para garantir a qualidade do código através da análise de cobertura de testes.

### 📋 Requisitos e Configuração
As regras de cobertura estão definidas no arquivo `pom.xml` de cada projeto, dentro da tag `<configuration>` do plugin. O build **falhará** automaticamente se os limites não forem atingidos.

| Projeto | Mínimo (Instruções) | Mínimo (Branches) | Status Atual |
|:-------:|:-------------------:|:-----------------:|:------------:|
| **TP1** | 80% | - | ✅ Aprovado |
| **TP2** | 80% | 70% | ✅ Aprovado |
| **TP4** | 85% | - | ✅ Aprovado |
| **TP5** | **90%** | **90%** | ✅ Aprovado |

### 🔍 Como Verificar
Após executar os testes (`mvn test` ou `mvn verify`), um relatório HTML detalhado é gerado.

**Localização do Relatório:**
```
<PASTA_DO_PROJETO>/target/site/jacoco/index.html
```
> Basta abrir este arquivo no seu navegador para visualizar a cobertura por pacote, classe e método.

---

## �📂 Localização dos PDFs

Os documentos PDF com os relatórios e evidências de cada entrega estão localizados nas seguintes pastas:

*   **TP1**: `TP1/tp1_entrega.pdf`
*   **TP2**: `TP2/tp2_pb.pdf`
*   **TP3**: `TP3/tp3_pb.pdf`
*   **TP4**: `TP4/tp4_pb.pdf`
*   **TP5**: `TP5/lucas_ferreira_tp5_pb.pdf`

---

## ⚙️ Instruções de Drivers (Selenium)

Os projetos utilizam **Selenium WebDriver** para testes de interface. O gerenciamento de drivers é feito automaticamente via `WebDriverManager`.

*   **TP2:** Configurado padrão para **Firefox** (`FirefoxDriver`).
*   **TP5/AT:** Configurado padrão para **Chrome** (`ChromeDriver`) em modo **Headless** (para compatibilidade com CI/CD).

Se desejar rodar em ambiente local visual (sem headless) ou trocar de navegador no TP2, consulte a classe `BaseSeleniumTest` ou `CadastroEventoE2ETest` no respectivo projeto.

---

## ✅ Detalhamento de Compliance (Exigência x Implementação)

Abaixo, o mapeamento detalhado de como cada requisito da rubrica foi atendido no código.

### 🏗️ TP1 - Fundamentos de Testes e Clean Code

**Objetivo:** Desenvolvimento de um sistema CRUD básico com foco em boas práticas de código e testes unitários robustos.

| ID | Exigência | Implementação / Localização | Status |
|----|-----------|-----------------------------|--------|
| **1.1** | **Sistema CRUD em Java** | `src/main/java/.../ClienteService.java` implementa lógica de criar, ler, atualizar e deletar. | ✅ Concluído |
| **1.2** | **Clean Code** | Código refatorado com nomes significativos, métodos pequenos e responsabilidade única. | ✅ Concluído |
| **1.3** | **Cobertura de Testes (>80%)** | Configurado no `pom.xml` (JaCoCo) e verificado nos relatórios em `target/site/jacoco`. | ✅ Concluído |
| **1.4** | **Setup/Teardown** | Uso de `@BeforeEach` em `ClienteServiceTest.java` para preparar o estado dos testes. | ✅ Concluído |
| **1.5** | **Tratamento de Exceções** | Testes validam comportamento em erro (ex: `assertThrows` em `ClienteServiceTest`). | ✅ Concluído |
| **1.6** | **Testes Baseados em Propriedades (Jqwik)** | Implementado em `ClienteServicePropertyTest.java` para gerar dados aleatórios de teste. | ✅ Concluído |

### 🌐 TP2 - Interface Web e Testes Automatizados (Selenium)

**Objetivo:** Criação de interface web e automação de testes E2E (End-to-End).

| ID | Exigência | Implementação / Localização | Status |
|----|-----------|-----------------------------|--------|
| **2.1** | **Interface Web (Thymeleaf/Spring MVC)** | `EventoController.java` mapeia rotas para templates em `src/main/resources/templates/`. | ✅ Concluído |
| **2.2** | **Selenium WebDriver** | Testes localizados em `src/test/java/.../tests/EventoCrudTest.java`. | ✅ Concluído |
| **2.3** | **Padrão Page Object** | Classes `EventoLoginPage.java`, `EventoListPage.java` abstraem a interação com o DOM. | ✅ Concluído |
| **2.4** | **Testes Negativos** | `EventoParameterizedTest.java` verifica validações de formulário (campos vazios/inválidos). | ✅ Concluído |
| **2.5** | **Cobertura (80% Linha / 70% Branch)** | Regras de enforcement configuradas no `pom.xml` plugin JaCoCo. | ✅ Concluído |

### 🛡️ TP3 - Qualidade, Robustez e Fuzz Testing

**Objetivo:** Evolução para um sistema mais robusto, com tratamento de falhas e testes avançados.

| ID | Exigência | Implementação / Localização | Status |
|----|-----------|-----------------------------|--------|
| **3.1** | **Novo CRUD (Gestão de Eventos)** | Implementado em `EventoService.java` e `EventoController.java`. | ✅ Concluído |
| **3.2** | **Fail Fast / Fail Gracefully** | `GlobalExceptionHandler.java` intercepta erros e `EventoService` valida nulos imediatamente. | ✅ Concluído |
| **3.3** | **Testes Parametrizados** | `EventoControllerIntegrationTest.java` usa `@ParameterizedTest` com `@CsvSource` para validar múltiplas entradas. | ✅ Concluído |
| **3.4** | **Fuzz Testing / Property-Based** | **(Adicionado na Verificação)** `EventoServicePropertyTest.java` usa Jqwik para bombardear o service com entradas aleatórias. | ✅ Concluído |
| **3.5** | **Simulação de Falhas** | Testes de integração simulam cenários de erro e recuperação. | ✅ Concluído |

### 🔄 TP4 - Integração e Refatoração (CI/CD Parte 1)

**Objetivo:** Integração dos sistemas e início da automação de build.

| ID | Exigência | Implementação / Localização | Status |
|----|-----------|-----------------------------|--------|
| **4.1** | **Integração de Componentes** | Projeto modificado para estrutura Maven unificada em `com-cliente-projeto`. | ✅ Concluído |
| **4.2** | **Refatoração Guiada por Testes** | Melhorias na injeção de dependência (Constructor Injection) em `EventoService`. | ✅ Concluído |
| **4.3** | **Pipeline de Integração Contínua** | Arquivos `.github/workflows/maven.yml` (ou similar) configurados para Build e Test. | ✅ Concluído |
| **4.4** | **Cobertura Mínima de 85%** | `pom.xml` atualizado com regra `<minimum>0.85</minimum>` no JaCoCo. | ✅ Concluído |

### 🚀 TP5 & AT - Entrega Final, CI/CD Completo e Segurança

**Objetivo:** Pipeline DevOps completo, qualidade de código estrita e documentação final.

| ID | Exigência | Implementação / Localização | Status |
|----|-----------|-----------------------------|--------|
| **5.1** | **Refatoração Final** | Uso de imutabilidade (final fields) e DTOs (`EventoDTO.java`) para desacoplar camadas. | ✅ Concluído |
| **5.2** | **Pipeline CI/CD Completo** | Workflow configurado para Build -> Test -> Security Audit -> Deploy (simulado/staging). | ✅ Concluído |
| **5.3** | **Cobertura Mínima de 90%** | **(Corrigido na Verificação)** `pom.xml` agora possui a execução `<check>` do JaCoCo configurada para 90% de instruções e branches. | ✅ Concluído |
| **5.4** | **Testes Pós-Deploy** | Testes E2E (`CadastroEventoE2ETest.java`) configurados para rodar contra o ambiente de staging. | ✅ Concluído |
| **5.5** | **Logs Customizados** | Uso de SLF4J/Logback configurado no `application.properties` e nas classes de serviço. | ✅ Concluído |
