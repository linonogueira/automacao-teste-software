package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.WebElement;

public class Lancamento {

    private String descricao;
    private String categoria;
    private String dataLancamento;
    private String valor;
    private String tipo;

    public Lancamento(String descricao, String categoria, String dataLancamento, String valor, String tipo) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataLancamento = dataLancamento;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }
}


