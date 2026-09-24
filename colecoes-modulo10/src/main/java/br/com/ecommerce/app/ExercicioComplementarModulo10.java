package br.com.ecommerce.app;

import br.com.ecommerce.colecoes.CatalogoPrecos;
import br.com.ecommerce.colecoes.FilaDePedidos;
import br.com.ecommerce.colecoes.GerenciadorSessoes;
import br.com.ecommerce.colecoes.UtilitariosCollections;
import br.com.ecommerce.modelo.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/**
 * Exercício Complementar Avançado — Módulo 10 (Tópicos em Coleções).
 * <p>
 * Aplicação única que integra, em sequência, os quatro tópicos finais do
 * material:
 * <ol>
 *     <li>Subconjuntos do SortedSet (subSet, headSet, tailSet)</li>
 *     <li>Contrato formal da interface Queue (offer, peek, poll)</li>
 *     <li>Métodos utilitários de Collections (shuffle, reverse, min, synchronizedList)</li>
 *     <li>Estrutura legada Hashtable percorrida via Enumeration</li>
 * </ol>
 */
public class ExercicioComplementarModulo10 {

    public static void main(String[] args) {
        parte1_SubconjuntosDoSortedSet();
        parte2_ContratoDeFila();
        parte3_UtilitariosDeCollections();
        parte4_HashtableComEnumeration();
    }

    // ------------------------------------------------------------------
    // 1) SortedSet: subSet, headSet, tailSet
    // ------------------------------------------------------------------
    private static void parte1_SubconjuntosDoSortedSet() {
        titulo("1) SUBCONJUNTOS DO SORTEDSET (TreeSet ordenado por preço)");

        CatalogoPrecos catalogo = new CatalogoPrecos();
        catalogo.adicionar(new Produto("P001", "Teclado Mecânico", new BigDecimal("250.00")));
        catalogo.adicionar(new Produto("P002", "Monitor 24 polegadas", new BigDecimal("899.90")));
        catalogo.adicionar(new Produto("P003", "Mouse Sem Fio", new BigDecimal("89.50")));
        catalogo.adicionar(new Produto("P004", "Headset Gamer", new BigDecimal("320.00")));
        catalogo.adicionar(new Produto("P005", "Webcam Full HD", new BigDecimal("199.90")));
        catalogo.adicionar(new Produto("P006", "Cadeira Gamer", new BigDecimal("1450.00")));

        System.out.println("Catálogo completo (ordem natural = por preço):");
        catalogo.getCatalogoCompleto().forEach(p -> System.out.println("  " + p));

        System.out.println("\nsubSet(100, 400) -> produtos entre R$100 (inclusive) e R$400 (exclusive):");
        SortedSet<Produto> faixa = catalogo.consultarPorFaixaDePreco(
                new BigDecimal("100.00"), new BigDecimal("400.00"));
        faixa.forEach(p -> System.out.println("  " + p));

        System.out.println("\nheadSet(300) -> produtos com preço menor que R$300 (o \"teto\"):");
        SortedSet<Produto> ateOTeto = catalogo.consultarAteOTeto(new BigDecimal("300.00"));
        ateOTeto.forEach(p -> System.out.println("  " + p));

        System.out.println("\ntailSet(300) -> produtos com preço a partir de R$300 (o \"piso\"):");
        SortedSet<Produto> aPartirDoPiso = catalogo.consultarAPartirDoPiso(new BigDecimal("300.00"));
        aPartirDoPiso.forEach(p -> System.out.println("  " + p));
    }

    // ------------------------------------------------------------------
    // 2) Queue: offer, peek, poll
    // ------------------------------------------------------------------
    private static void parte2_ContratoDeFila() {
        titulo("2) CONTRATO DE FILA (Queue<String> com LinkedList)");

        FilaDePedidos fila = new FilaDePedidos();
        fila.novoPedido("PED-1001");
        fila.novoPedido("PED-1002");
        fila.novoPedido("PED-1003");

        System.out.println("Tamanho da fila após 3 offer(): " + fila.tamanho());
        System.out.println("peek() -> próximo pedido a ser atendido (sem remover): " + fila.proximoPedido());

        System.out.println("\nAtendendo pedidos com poll() (ordem FIFO):");
        while (!fila.estaVazia()) {
            System.out.println("  Atendido: " + fila.atenderPedido());
        }

        System.out.println("\npeek() com a fila vazia (retorna null, sem lançar exceção): " + fila.proximoPedido());
        System.out.println("poll() com a fila vazia (retorna null, sem lançar exceção): " + fila.atenderPedido());
    }

    // ------------------------------------------------------------------
    // 3) Collections: shuffle, reverse, min, synchronizedList
    // ------------------------------------------------------------------
    private static void parte3_UtilitariosDeCollections() {
        titulo("3) MÉTODOS UTILITÁRIOS DE COLLECTIONS");

        List<Produto> produtos = new ArrayList<>(List.of(
                new Produto("P001", "Teclado Mecânico", new BigDecimal("250.00")),
                new Produto("P002", "Monitor 24 polegadas", new BigDecimal("899.90")),
                new Produto("P003", "Mouse Sem Fio", new BigDecimal("89.50")),
                new Produto("P004", "Headset Gamer", new BigDecimal("320.00"))
        ));

        UtilitariosCollections utilitarios = new UtilitariosCollections();

        System.out.println("Lista original:");
        produtos.forEach(p -> System.out.println("  " + p));

        utilitarios.embaralharProdutos(produtos);
        System.out.println("\nApós Collections.shuffle():");
        produtos.forEach(p -> System.out.println("  " + p));

        utilitarios.inverterOrdem(produtos);
        System.out.println("\nApós Collections.reverse() (inverte a ordem atual, embaralhada):");
        produtos.forEach(p -> System.out.println("  " + p));

        Produto maisBarato = utilitarios.produtoMaisBarato(produtos);
        System.out.println("\nCollections.min() -> produto mais barato: " + maisBarato);

        Produto primeiroAlfabetico = utilitarios.produtoPrimeiroEmOrdemAlfabetica(produtos);
        System.out.println("Collections.min() com Comparator -> primeiro em ordem alfabética: " + primeiroAlfabetico);

        List<Produto> listaSincronizada = utilitarios.criarListaDeProdutosThreadSafe();
        listaSincronizada.add(maisBarato);
        System.out.println("\nCollections.synchronizedList() -> wrapper criado: " +
                listaSincronizada.getClass().getName() + " (acesso concorrente seguro)");
    }

    // ------------------------------------------------------------------
    // 4) Hashtable + Enumeration
    // ------------------------------------------------------------------
    private static void parte4_HashtableComEnumeration() {
        titulo("4) ESTRUTURA LEGADA: HASHTABLE + ENUMERATION");

        GerenciadorSessoes sessoes = new GerenciadorSessoes();
        sessoes.abrirSessao("user01", "login às 09:15 - dispositivo: desktop");
        sessoes.abrirSessao("user02", "login às 09:42 - dispositivo: mobile");
        sessoes.abrirSessao("user03", "login às 10:03 - dispositivo: tablet");

        System.out.println("Total de sessões ativas: " + sessoes.totalDeSessoesAtivas());

        System.out.println("\nPercorrendo chaves via Enumeration.keys():");
        sessoes.listarSessoesAtivas();

        System.out.println("\nPercorrendo valores via Enumeration.elements():");
        sessoes.listarInformacoesDeSessao();

        sessoes.encerrarSessao("user02");
        System.out.println("\nApós encerrar a sessão de user02, total de sessões ativas: " +
                sessoes.totalDeSessoesAtivas());
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("========================================================");
        System.out.println(texto);
        System.out.println("========================================================");
    }
}
