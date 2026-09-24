package br.com.ecommerce.modelo;

import java.math.BigDecimal;

/**
 * Classe POJO (Plain Old Java Object) que representa um produto do catálogo.
 * Apenas transporta os dados em memória, espelhando os atributos da tabela
 * "produto" do banco de dados. Não contém nenhuma lógica de conexão ou SQL.
 */
public class Produto {

    private String codigo;
    private String nome;
    private BigDecimal preco;
    private int quantidadeEstoque;

    public Produto() {
    }

    public Produto(String codigo, String nome, BigDecimal preco, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }
}
