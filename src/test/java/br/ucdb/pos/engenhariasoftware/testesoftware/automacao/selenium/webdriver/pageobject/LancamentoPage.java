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

        if(tipo == TipoLancamento.SAIDA) {
            driver.findElement(By.id("tipoLancamento2")).click();
        }else{
            driver.findElement(By.id("tipoLancamento1")).click();
        }

        WebElement valor = driver.findElement(By.id("valor"));
        valor.sendKeys(String.valueOf(valorLancamento));

        WebElement categoria = driver.findElement(By.id("categoria"));
        categoria.click();
        categoria.sendKeys(categoriaLancamento);

        valor.sendKeys(String.valueOf(valorLancamento));
        driver.findElement(By.id("btnSalvar")).click();
    }

    public String EditarLancamento(String descricaoLancamento) {
        Lancamento lancamento = BuscarLancamento(descricaoLancamento);
        WebElement body = driver.findElement(By.tagName("tbody"));
        WebElement div = body.findElement(By.tagName("div"));
        List<WebElement> botoes = div.findElements(By.tagName("a"));
        botoes.get(0).click();


        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.clear();
        String descricaoEditada = "Editado - "+lancamento.getDescricao();
        descricao.sendKeys(descricaoEditada);
        driver.findElement(By.id("btnSalvar")).click();
        return descricaoEditada;
    }

    public Lancamento BuscarLancamento(String descricaoLancamento) {

        WebElement busca = driver.findElement(By.id("itemBusca"));
        busca.click();
        busca.sendKeys(descricaoLancamento);

        WebElement botao = driver.findElement(By.id("bth-search"));
        botao.click();

        WebElement tabela = driver.findElement(By.id("tabelaLancamentos"));
        WebElement tbody = tabela.findElement(By.tagName("tbody"));
        WebElement tr = tbody.findElement(By.tagName("tr"));
        List<WebElement> coluna = tr.findElements(By.tagName("td"));
        Lancamento lancamento = new Lancamento(coluna.get(0).getText(), coluna.get(1).getText(), coluna.get(2).getText(), coluna.get(3).getText(), coluna.get(4).getText());
        return lancamento;
    }

    public void ExcluirLancamento(String descricaoLancamento) {
        Lancamento lancamento = BuscarLancamento(descricaoLancamento);
        WebElement body = driver.findElement(By.tagName("tbody"));
        WebElement div = body.findElement(By.tagName("div"));
        List<WebElement> botoes = div.findElements(By.tagName("a"));
        botoes.get(1).click();
    }

    public  BigDecimal GetTotalEntrada(){

        BigDecimal totalEntrada = new BigDecimal(0);

        WebElement tabela = driver.findElement(By.id("tabelaLancamentos"));
        WebElement tbody = tabela.findElement(By.tagName("tbody"));
        List<WebElement> linhas = tbody.findElements(By.tagName("tr"));

        for(WebElement linha : linhas){

            List<WebElement> colunas = linha.findElements(By.tagName("td"));
            Lancamento lancamento = new Lancamento(colunas.get(0).getText(), colunas.get(1).getText(),colunas.get(2).getText(), colunas.get(3).getText(), colunas.get(4).getText());

            if(lancamento.getTipo().equals(TipoLancamento.ENTRADA.getDescricao())){
                totalEntrada = totalEntrada.add(new BigDecimal(lancamento.getValor().replaceAll("\\.", "").replaceAll(",",".")));
            }

        }
        return totalEntrada;
    }

    public  BigDecimal GetTotalSaida(){

        BigDecimal totalSaida = new BigDecimal(0);

        WebElement tabela = driver.findElement(By.id("tabelaLancamentos"));
        WebElement tbody = tabela.findElement(By.tagName("tbody"));
        List<WebElement> linhas = tbody.findElements(By.tagName("tr"));

        for(WebElement linha : linhas){

            List<WebElement> colunas = linha.findElements(By.tagName("td"));
            Lancamento lancamento = new Lancamento(colunas.get(0).getText(), colunas.get(1).getText(),colunas.get(2).getText(), colunas.get(3).getText(), colunas.get(4).getText());

            if(lancamento.getTipo().equals(TipoLancamento.SAIDA.getDescricao())) {
                totalSaida = totalSaida.add(new BigDecimal(lancamento.getValor().replaceAll("\\.", "").replaceAll(",",".")));
            }


        }
        return totalSaida;
    }

    public BigDecimal GetTotalEntradaCampo(){

        BigDecimal totalEntrada = new BigDecimal(0);

        WebElement tabela = driver.findElement(By.id("tabelaLancamentos"));
        WebElement tfoot = tabela.findElement(By.tagName("tfoot"));
        List<WebElement> linhas = tfoot.findElements(By.tagName("tr"));

        if(linhas.size() == 2){

            String valorEntrada = linhas.get(1).findElement(By.tagName("th")).findElement(By.tagName("span")).getText();

            totalEntrada = new BigDecimal(valorEntrada.replaceAll("\\.","").replaceAll(",","."));

        }

        return totalEntrada;
    }

    public BigDecimal GetTotalSaidaCampo(){

        BigDecimal totalSaida = new BigDecimal(0);

        WebElement tabela = driver.findElement(By.id("tabelaLancamentos"));
        WebElement tfoot = tabela.findElement(By.tagName("tfoot"));
        List<WebElement> linhas = tfoot.findElements(By.tagName("tr"));

        if(linhas.size() == 2){

            String valorSaida = linhas.get(0).findElement(By.tagName("th")).findElement(By.tagName("span")).getText();

            totalSaida = new BigDecimal(valorSaida.replaceAll("\\.","").replaceAll(",","."));

        }

        return totalSaida;
    }
}


