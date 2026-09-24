package br.com.ecommerce.colecoes;

import br.com.ecommerce.modelo.Produto;

import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * MISSÃO — Subconjuntos do SortedSet.
 * <p>
 * Mantém o catálogo em um {@link TreeSet}, que implementa a interface
 * {@link SortedSet} (uma árvore binária balanceada). Como os produtos são
 * ordenados naturalmente por preço (ver {@link Produto#compareTo}), a
 * própria estrutura de dados já nos entrega, em O(log n), visões
 * (janelas) do catálogo por faixa de preço — sem precisar varrer a
 * coleção inteira com um laço de repetição.
 */
public class CatalogoPrecos {

    private final TreeSet<Produto> catalogoPorPreco = new TreeSet<>();

    public void adicionar(Produto produto) {
        catalogoPorPreco.add(produto);
    }

    public TreeSet<Produto> getCatalogoCompleto() {
        return catalogoPorPreco;
    }

    /**
     * subSet(de, ate): devolve a faixa de produtos cujo preço está entre
     * "de" (inclusive) e "ate" (exclusive) — o limite superior nunca entra
     * na faixa, exatamente como no contrato da interface SortedSet.
     */
    public SortedSet<Produto> consultarPorFaixaDePreco(BigDecimal precoDe, BigDecimal precoAte) {
        Produto limiteInferior = Produto.marcadorDePreco(precoDe);
        Produto limiteSuperior = Produto.marcadorDePreco(precoAte);
        return catalogoPorPreco.subSet(limiteInferior, limiteSuperior);
    }

    /**
     * headSet(teto): devolve todos os produtos com preço estritamente
     * menor que o teto informado — útil para responder "o que cabe no
     * meu orçamento de até R$ X?".
     */
    public SortedSet<Produto> consultarAteOTeto(BigDecimal precoTeto) {
        Produto marcadorTeto = Produto.marcadorDePreco(precoTeto);
        return catalogoPorPreco.headSet(marcadorTeto);
    }

    /**
     * tailSet(piso): devolve todos os produtos com preço maior ou igual
     * ao piso informado — útil para responder "quais produtos custam
     * a partir de R$ X?".
     */
    public SortedSet<Produto> consultarAPartirDoPiso(BigDecimal precoPiso) {
        Produto marcadorPiso = Produto.marcadorDePreco(precoPiso);
        return catalogoPorPreco.tailSet(marcadorPiso);
    }
}
