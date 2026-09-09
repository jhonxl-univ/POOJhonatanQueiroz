import java.util.ArrayList;
import java.util.Collections;

public class Main {

    // =========================================================
    // EXERCÍCIO 1.2
    // =========================================================

    public static Produto buscarPorCodigo(
            ArrayList<Produto> lista,
            String codigo) {

        for (Produto produto : lista) {

            if (produto.getCodigo().equalsIgnoreCase(codigo)) {
                return produto;
            }
        }

        return null;
    }

    public static void aplicarReajusteGeral(
            ArrayList<Produto> lista,
            double percentual) {

        for (Produto produto : lista) {

            double novoPreco =
                    produto.getPreco() * (1 + percentual / 100);

            produto.setPreco(novoPreco);
        }
    }

    public static void main(String[] args) {

        // =====================================================
        // NÍVEL 1 - ARRAYLIST
        // =====================================================

        System.out.println("==========================================");
        System.out.println("NÍVEL 1 - ARRAYLIST");
        System.out.println("==========================================");

        ArrayList<Produto> listaProdutos = new ArrayList<>();

        Produto p1 =
                new Produto("P001", "Notebook", 3500.00);

        Produto p2 =
                new Produto("P002", "Mouse", 120.00);

        Produto p3 =
                new Produto("P003", "Teclado", 250.00);

        Produto p4 =
                new Produto("P004", "Monitor", 1200.00);

        Produto p5 =
                new Produto("P005", "Celular", 2500.00);


        // Adicionando produtos
        listaProdutos.add(p1);
        listaProdutos.add(p2);
        listaProdutos.add(p3);
        listaProdutos.add(p4);

        System.out.println("\nProdutos cadastrados:");

        for (Produto produto : listaProdutos) {
            System.out.println(
                    produto.getCodigo() + " - " +
                    produto.getNome() + " - R$ " +
                    String.format("%.2f", produto.getPreco())
            );
        }


        // =====================================================
        // REMOVE POR ÍNDICE
        // =====================================================

        System.out.println("\nRemovendo produto pelo índice...");

        listaProdutos.remove(3);

        System.out.println(
                "Quantidade atual: " +
                listaProdutos.size()
        );


        // =====================================================
        // REMOVE POR OBJETO
        // =====================================================

        System.out.println("\nAdicionando novamente o Monitor...");

        listaProdutos.add(p4);

        System.out.println("Removendo o Monitor pelo objeto...");

        listaProdutos.remove(p4);

        System.out.println(
                "Quantidade atual: " +
                listaProdutos.size()
        );


        // Adiciona novamente para os próximos exercícios.
        listaProdutos.add(p4);
        listaProdutos.add(p5);


        // =====================================================
        // EXERCÍCIO 1.2 - BUSCA
        // =====================================================

        System.out.println("\n==========================================");
        System.out.println("EXERCÍCIO 1.2 - BUSCA");
        System.out.println("==========================================");

        Produto encontrado =
                buscarPorCodigo(listaProdutos, "P003");

        if (encontrado != null) {
            System.out.println("Produto encontrado:");
            System.out.println(encontrado);
        } else {
            System.out.println("Produto não encontrado.");
        }


        // Busca inexistente
        Produto naoEncontrado =
                buscarPorCodigo(listaProdutos, "P999");

        if (naoEncontrado != null) {
            System.out.println(naoEncontrado);
        } else {
            System.out.println(
                    "Produto P999 não encontrado."
            );
        }


        // =====================================================
        // REAJUSTE DE 10%
        // =====================================================

        System.out.println("\nAplicando reajuste de 10%...");

        aplicarReajusteGeral(listaProdutos, 10);

        for (Produto produto : listaProdutos) {

            System.out.printf(
                    "%s - %s - R$ %.2f%n",
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getPreco()
            );
        }


        // =====================================================
        // NÍVEL 2.1 - CATEGORIA
        // =====================================================

        System.out.println("\n==========================================");
        System.out.println("NÍVEL 2.1 - CATEGORIAS");
        System.out.println("==========================================");

        Categoria informatica =
                new Categoria(1, "Informática");

        Categoria eletronicos =
                new Categoria(2, "Eletrônicos");

        Categoria celulares =
                new Categoria(3, "Celulares");


        informatica.adicionarProduto(p1);
        informatica.adicionarProduto(p2);
        informatica.adicionarProduto(p3);
        informatica.adicionarProduto(p4);

        eletronicos.adicionarProduto(p3);
        eletronicos.adicionarProduto(p4);

        celulares.adicionarProduto(p5);


        informatica.listarProdutos();

        System.out.println();

        eletronicos.listarProdutos();

        System.out.println();

        celulares.listarProdutos();


        // =====================================================
        // NÍVEL 2.2 - FABRICANTE
        // =====================================================

        System.out.println("\n==========================================");
        System.out.println("NÍVEL 2.2 - FABRICANTE");
        System.out.println("==========================================");

        Fabricante fabricanteA =
                new Fabricante(
                        "11.111.111/0001-11",
                        "Fabricante A"
                );

        Fabricante fabricanteB =
                new Fabricante(
                        "22.222.222/0001-22",
                        "Fabricante B"
                );


        // Produto inicialmente associado ao Fabricante A.
        p1.setFabricante(fabricanteA);

        System.out.println(
                "Fabricante do produto: " +
                p1.getFabricante().getNome()
        );

        System.out.println(
                "Produtos do Fabricante A: " +
                fabricanteA.getListaProdutos().size()
        );

        // Troca para Fabricante B.
        p1.setFabricante(fabricanteB);

        System.out.println(
                "\nApós trocar o fabricante:"
        );

        System.out.println(
                "Fabricante do produto: " +
                p1.getFabricante().getNome()
        );

        System.out.println(
                "Produtos do Fabricante A: " +
                fabricanteA.getListaProdutos().size()
        );

        System.out.println(
                "Produtos do Fabricante B: " +
                fabricanteB.getListaProdutos().size()
        );


        // =====================================================
        // NÍVEL 3 - N:M / CLASSE DE ASSOCIAÇÃO
        // =====================================================

        System.out.println("\n==========================================");
        System.out.println("NÍVEL 3 - NOTA FISCAL E ITENS");
        System.out.println("==========================================");

        Produto arroz =
                new Produto(
                        "A001",
                        "Arroz",
                        25.00
                );

        Produto feijao =
                new Produto(
                        "A002",
                        "Feijão",
                        9.00
                );

        Produto cafe =
                new Produto(
                        "A003",
                        "Café",
                        18.00
                );


        NotaFiscal nota =
                new NotaFiscal(
                        1001,
                        "09/09/2026"
                );


        Item item1 =
                new Item(
                        nota,
                        arroz,
                        2,
                        25.00
                );

        Item item2 =
                new Item(
                        nota,
                        feijao,
                        5,
                        9.00
                );

        Item item3 =
                new Item(
                        nota,
                        cafe,
                        3,
                        18.00
                );


        nota.listarItens();


        // =====================================================
        // NÍVEL 4.1 - COMPARABLE
        // =====================================================

        System.out.println("\n==========================================");
        System.out.println("NÍVEL 4.1 - COMPARABLE");
        System.out.println("==========================================");

        ArrayList<Produto> listaOrdenacao =
                new ArrayList<>();

        Produto produtoA =
                new Produto("010", "Teclado", 250.00);

        Produto produtoB =
                new Produto("020", "Celular", 2500.00);

        Produto produtoC =
                new Produto("030", "Notebook", 3500.00);

        Produto produtoD =
                new Produto("040", "Mouse", 120.00);

        Produto produtoE =
                new Produto("050", "Monitor", 1200.00);


        // Adicionando fora da ordem alfabética.
        listaOrdenacao.add(produtoA);
        listaOrdenacao.add(produtoB);
        listaOrdenacao.add(produtoC);
        listaOrdenacao.add(produtoD);
        listaOrdenacao.add(produtoE);


        System.out.println("Antes da ordenação:");

        for (Produto produto : listaOrdenacao) {
            System.out.println(produto);
        }


        // Ordenação natural definida pelo Comparable.
        Collections.sort(listaOrdenacao);

        System.out.println("\nDepois da ordenação por nome:");

        for (Produto produto : listaOrdenacao) {
            System.out.println(produto);
        }


        // =====================================================
        // NÍVEL 4.2 - COMPARATOR POR PREÇO
        // =====================================================

        System.out.println("\n==========================================");
        System.out.println("NÍVEL 4.2 - COMPARATOR");
        System.out.println("==========================================");


        // Preço crescente.
        Collections.sort(
                listaOrdenacao,
                new ComparadorPorPreco()
        );

        System.out.println("\nPreço crescente:");

        for (Produto produto : listaOrdenacao) {
            System.out.println(produto);
        }


        // Preço decrescente.
        Collections.sort(
                listaOrdenacao,
                Collections.reverseOrder(
                        new ComparadorPorPreco()
                )
        );

        System.out.println("\nPreço decrescente:");

        for (Produto produto : listaOrdenacao) {
            System.out.println(produto);
        }


        // =====================================================
        // ORDENAÇÃO POR CÓDIGO
        // =====================================================

        Collections.sort(
                listaOrdenacao,
                new ComparadorPorCodigo()
        );

        System.out.println("\nOrdem por código:");

        for (Produto produto : listaOrdenacao) {
            System.out.println(produto);
        }


        // =====================================================
        // FINAL
        // =====================================================

        System.out.println("\n==========================================");
        System.out.println("TODOS OS EXERCÍCIOS FORAM EXECUTADOS");
        System.out.println("==========================================");
    }
}