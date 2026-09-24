package br.com.ecommerce.modelo;

import java.math.BigDecimal;

/**
 * POJO que representa um produto do catálogo.
 * <p>
 * Implementa {@link Comparable} tendo o <b>preço</b> como critério de
 * ordenação natural. Essa escolha é o que permite usar {@link java.util.TreeSet}
 * como uma árvore binária balanceada ordenada por preço e, a partir dela,
 * extrair as visões de subconjunto exigidas pelo exercício:
 * {@code subSet}, {@code headSet} e {@code tailSet}.
 */
public class Produto implements Comparable<Produto> {

    private final String codigo;
    private final String nome;
    private final BigDecimal preco;

    public Produto(String codigo, String nome, BigDecimal preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Construtor de conveniência usado apenas para criar "produtos-marcadores",
     * isto é, objetos que servem unicamente como limite (bound) de faixa nas
     * consultas subSet/headSet/tailSet, sem representar um produto real do catálogo.
     */
    public static Produto marcadorDePreco(BigDecimal preco) {
        return new Produto(null, "(marcador de faixa)", preco);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * Critério de ordenação natural: por preço, em ordem crescente.
     * É esse método que o TreeSet invoca internamente para posicionar
     * cada produto na árvore binária balanceada (Red-Black Tree),
     * garantindo inserção e busca em O(log n).
     * <p>
     * Atenção: o TreeSet usa exclusivamente compareTo() (e não equals/hashCode)
     * para decidir se dois elementos são "iguais". Por isso, quando os preços
     * empatam, desempata-se pelo código do produto — evitando que dois
     * produtos distintos com o mesmo preço sejam tratados como duplicados
     * e um deles seja silenciosamente descartado pelo add().
     */
    @Override
    public int compareTo(Produto outro) {
        int comparacaoPreco = this.preco.compareTo(outro.preco);
        if (comparacaoPreco != 0) {
            return comparacaoPreco;
        }
        if (this.codigo == null || outro.codigo == null) {
            return 0;
        }
        return this.codigo.compareTo(outro.codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return codigo != null && codigo.equals(produto.codigo);
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return nome + " (" + codigo + ") - R$ " + preco;
    }
}
