package com.cliente.projeto.crudpb.e2e.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FormularioEventoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Seletores dos elementos
    private By campoNome = By.id("nome");
    private By campoDescricao = By.id("descricao");

    // Seletores para o novo campo de usuário
    private By selectUsuario = By.id("usuarioId");

    // Buscando por qualquer erro (Global .alert-danger OU Campo .text-danger)
    // Isso resolve o problema do teste falhar esperando erro global quando é erro
    // de campo
    private By containerMensagemErro = By.cssSelector(".alert-danger, .text-danger");

    public FormularioEventoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void preencherFormulario(String nome, String descricao, Long usuarioId) {
        // Nome - Espera estar visível
        WebElement inputNome = wait.until(ExpectedConditions.visibilityOfElementLocated(campoNome));
        inputNome.clear();
        inputNome.sendKeys(nome);

        // Descrição
        WebElement inputDescricao = driver.findElement(campoDescricao);
        inputDescricao.clear();
        inputDescricao.sendKeys(descricao);

        // Se um usuarioId for fornecido, selecione-o no dropdown
        if (usuarioId != null) {
            WebElement selectEl = wait.until(ExpectedConditions.visibilityOfElementLocated(selectUsuario));
            Select dropdownUsuario = new Select(selectEl);
            dropdownUsuario.selectByValue(usuarioId.toString());
        }
    }

    public void submeter() {
        By seletorBotaoSalvar = By.cssSelector("button[type='submit']");
        driver.findElement(seletorBotaoSalvar).click();
    }

    public String getMensagemDeErroVisivel() {
        // Espera qualquer erro (global ou de campo) ficar visível
        WebElement erroEl = wait.until(ExpectedConditions.visibilityOfElementLocated(containerMensagemErro));
        return erroEl.getText();
    }
}