package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.LancamentoPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.ListaLancamentosPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.TipoLancamento;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.testng.Assert.assertTrue;

public class LancamentoTest {

    private WebDriver driver;
    private ListaLancamentosPage listaLancamentosPage;
    private LancamentoPage lancamentoPage;

    @BeforeClass
    private void inicialliza() {
        boolean windows = System.getProperty("os.name").toUpperCase().contains("WIN");
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("user.dir") + "/src/test/resources/drivers/" +
                        "/geckodriver" + (windows ? ".exe" : ""));
        driver = new FirefoxDriver();
        listaLancamentosPage = new ListaLancamentosPage(driver);
        lancamentoPage = new LancamentoPage(driver);
    }

    @Test
    public void criaLancamento(){
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
        final String descricaoLancamento = "Lançando saída automatizada " + dataHora.format(formatoLancamento);
        final BigDecimal valor = getValorLancamento();
        String categoria = getCategoria();
        lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA, categoria);

        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA));
    }

    @Test
    public void exercicio1(){
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
        final String descricaoLancamento = "Lançando saída automatizada " + dataHora.format(formatoLancamento);
        final BigDecimal valor = getValorLancamento();
        String categoria = getCategoria();
        lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA, categoria);

        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA));
    }

    @Test
    public void exercicio2(){
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
        String descricaoLancamento = "CR0147 - Lançando Entrada automatizada " + dataHora.format(formatoLancamento);
        final BigDecimal valor = getValorLancamento();
        String categoria = getCategoria();
        lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.ENTRADA, categoria);
        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.ENTRADA));

        descricaoLancamento = lancamentoPage.EditarLancamento(descricaoLancamento);
        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.ENTRADA));

    }
    @Test
    public void exercicio3(){
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
        String descricaoLancamento = "CR0149 - Lançando saída automatizada " + dataHora.format(formatoLancamento);
        final BigDecimal valor = getValorLancamento();
        String categoria = getCategoria();
        lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA, categoria);
        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA));

        descricaoLancamento = lancamentoPage.EditarLancamento(descricaoLancamento);
        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA));

        lancamentoPage.ExcluirLancamento(descricaoLancamento);
        assertTrue(!listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA));
    }

    @Test
    public void exercicio4(){
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
        String descricaoLancamento = "Lançando Entrada automatizada " + dataHora.format(formatoLancamento);
        final BigDecimal valor = getValorLancamento();
        String categoria = getCategoria();
        lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.ENTRADA, categoria);
        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.ENTRADA));

        BigDecimal totalEntradaTabela =  lancamentoPage.GetTotalEntrada();
        BigDecimal totalSaidaTabela =  lancamentoPage.GetTotalSaida();

        BigDecimal totalEntradaCampo =  lancamentoPage.GetTotalEntradaCampo();
        BigDecimal totalSaidaCampo =  lancamentoPage.GetTotalSaidaCampo();

        assertTrue(totalEntradaTabela.compareTo(totalEntradaCampo) == 0);
        assertTrue(totalSaidaTabela.compareTo(totalSaidaCampo) == 0);
    }

    @AfterClass
    private void finaliza(){
        driver.quit();
    }

    private BigDecimal getValorLancamento() {

        boolean  aplicaVariante = (System.currentTimeMillis() % 3) == 0;
        int fator = 10;
        long mim = 30;
        long max = 900;
        if(aplicaVariante){
            mim /= fator;
            max /= fator;
        }
        return new BigDecimal(( 1 + (Math.random() * (max - mim)))).setScale(2, RoundingMode.HALF_DOWN);
    }

    private String getCategoria() {
        int opcao = 0;
        int quantidadeopcoes = 8;
        Random random = new Random();
        opcao = random.nextInt(quantidadeopcoes) + 1;

        if (opcao == 1)
            return "ALIMENTACAO";
        if (opcao == 2)
            return "SALARIO";
        if (opcao == 3)
            return "LAZER";
        if (opcao == 4)
            return "TELEFONE_INTERNET";
        if (opcao == 5)
            return "CARRO";
        if (opcao == 6)
            return "EMPRESTIMO";
        if (opcao == 7)
            return "INVESTIMENTOS";

        return "OUTROS";
    }




    
}


