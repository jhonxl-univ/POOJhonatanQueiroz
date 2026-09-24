package br.com.ecommerce.colecoes;

import br.com.ecommerce.modelo.Produto;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * MISSÃO — Métodos utilitários da classe Collections.
 * <p>
 * A classe {@link Collections} (não confundir com a interface
 * {@link java.util.Collection}) reúne métodos estáticos que operam sobre
 * qualquer implementação de List/Set/Map, sem exigir que o programador
 * escreva os algoritmos de novo a cada projeto.
 */
public class UtilitariosCollections {

    /**
     * Collections.shuffle(): embaralha aleatoriamente a ordem dos elementos
     * da lista — útil, por exemplo, para sortear a ordem de destaque de
     * produtos em uma vitrine promocional.
     */
    public void embaralharProdutos(List<Produto> produtos) {
        Collections.shuffle(produtos);
    }

    /**
     * Collections.reverse(): inverte, em O(n), a ordem atual dos elementos
     * da lista (não faz nenhuma nova ordenação — apenas espelha a sequência).
     */
    public void inverterOrdem(List<Produto> produtos) {
        Collections.reverse(produtos);
    }

    /**
     * Collections.min(): percorre a coleção e devolve o menor elemento
     * segundo a ordenação natural (Comparable) — no caso de Produto,
     * o de menor preço.
     */
    public Produto produtoMaisBarato(List<Produto> produtos) {
        return Collections.min(produtos);
    }

    /**
     * Collections.min() também aceita um Comparator customizado — aqui,
     * localizamos o produto de nome alfabeticamente menor, independente
     * do critério de ordenação natural da classe.
     */
    public Produto produtoPrimeiroEmOrdemAlfabetica(List<Produto> produtos) {
        return Collections.min(produtos, Comparator.comparing(Produto::getNome));
    }

    /**
     * Collections.synchronizedList(): envolve (decora) uma List comum,
     * originalmente não thread-safe (como a LinkedList abaixo), em um
     * wrapper sincronizado, tornando seguro o acesso concorrente por
     * múltiplas threads — de forma semelhante ao que o Vector já faz
     * nativamente, mas aplicável a qualquer implementação de List.
     */
    public List<Produto> criarListaDeProdutosThreadSafe() {
        List<Produto> listaComum = new LinkedList<>();
        return Collections.synchronizedList(listaComum);
    }
}
