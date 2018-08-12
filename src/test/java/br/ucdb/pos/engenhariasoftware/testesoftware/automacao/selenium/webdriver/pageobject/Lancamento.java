package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LancamentoPage {

    private WebDriver driver;

    public LancamentoPage(final WebDriver driver){
        this.driver = driver;
    }

    public void cria(final String descricaoLancamento, final BigDecimal valorLancamento,
                     LocalDateTime dataHora, TipoLancamento tipo, final String categoriaLancamento){

        if(tipo == TipoLancamento.SAIDA) {
            driver.findElement(By.id("tipoLancamento2")).click(); // informa lançamento: SAÍDA
        }else{
            driver.findElement(By.id("tipoLancamento1")).click(); // informa lançamento: ENTRADA
        }

        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.sendKeys(descricaoLancamento);

        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        WebElement dataLancamento = driver.findElement(By.name("dataLancamento"));
        dataLancamento.sendKeys(dataHora.format(formatoDataLancamento));

        WebElement valor = driver.findElement(By.id("valor"));
        driver.findElement(By.id("tipoLancamento2")).click();
        valor.sendKeys(String.valueOf(valorLancamento));

        WebElement categoria = driver.findElement(By.id("categoria"));
        categoria.click();
        categoria.sendKeys(categoriaLancamento);

        valor.sendKeys(String.valueOf(valorLancamento));
        driver.findElement(By.id("btnSalvar")).click();
    }

    public void EditarLancamento(String descricaoLancamento) {
        WebElement busca = driver.findElement(By.id("itemBusca"));
        busca.click();
        busca.sendKeys(descricaoLancamento);

        WebElement botao = driver.findElement(By.id("bth-search"));
        botao.click();

        busca.click();

    }
}


