package com.cliente.projeto.crudpb.e2e.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FormularioEventoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campoNome = By.id("nome");
    private By campoDescricao = By.id("descricao");
    private By botaoSalvar = By.id("btn-salvar");


    private By containerMensagemErro = By.id("validation-errors");

    public FormularioEventoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void preencherFormulario(String nome, String descricao) {
        driver.findElement(campoNome).clear();
        driver.findElement(campoNome).sendKeys(nome);

        driver.findElement(campoDescricao).clear();
        driver.findElement(campoDescricao).sendKeys(descricao);
    }

    public void submeter() {
        driver.findElement(botaoSalvar).click();
    }

    public String getMensagemDeErroVisivel() {
        WebElement erroEl = wait.until(ExpectedConditions.visibilityOfElementLocated(containerMensagemErro));
        return erroEl.getText();
    }
}