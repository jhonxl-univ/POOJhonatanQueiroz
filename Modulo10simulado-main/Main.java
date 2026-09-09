import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // =========================================================
        // MISSÃO 1 - CONTRATOS DE SERVIÇO E VALIDAÇÃO DE TIPOS
        // =========================================================

        System.out.println("\n========================================");
        System.out.println("MISSÃO 1 - RASTREAMENTO");
        System.out.println("========================================");

        PacoteCorreios pacote = new PacoteCorreios("BR123456789");
        CargaTransportadora carga = new CargaTransportadora("CT987654");
        EntregaExpressa entrega = new EntregaExpressa("EX555555");

        CentralRastreamento.inspecionarItem(pacote);
        CentralRastreamento.inspecionarItem(carga);
        CentralRastreamento.inspecionarItem(entrega);

        // Objeto que não implementa Rastreavel
        String objetoComum = "Produto sem rastreamento";

        CentralRastreamento.inspecionarItem(objetoComum);


        // =========================================================
        // MISSÃO 2 - FILAS, LISTAS E CONCORRÊNCIA
        // =========================================================

        System.out.println("\n========================================");
        System.out.println("MISSÃO 2 - FILAS E LISTAS");
        System.out.println("========================================");

        /*
         * A interface List está sendo associada a uma LinkedList.
         *
         * LinkedList possui desempenho superior ao ArrayList
         * quando existem muitas inserções e remoções no início
         * ou no meio da estrutura, pois seus elementos são ligados
         * por nós.
         *
         * No ArrayList, uma inserção no início pode exigir o
         * deslocamento dos elementos seguintes.
         */

        List<String> fila = new LinkedList<>();

        // Inserção no final
        fila.add("Pedido 001");
        fila.add("Pedido 002");
        fila.add("Pedido 003");

        // Inserção no início
        fila.add(0, "Pedido Prioritário");

        System.out.println("Fila de pedidos:");
        for (String pedido : fila) {
            System.out.println(pedido);
        }

        System.out.println("\nPrimeiro pedido: " + fila.get(0));
        System.out.println("Último pedido: " + fila.get(fila.size() - 1));


        /*
         * Vector:
         *
         * Vector possui métodos sincronizados (synchronized),
         * oferecendo maior segurança em operações concorrentes
         * envolvendo múltiplas threads.
         *
         * ArrayList não possui essa sincronização automática.
         *
         * A sincronização, porém, pode gerar um custo de desempenho.
         */

        Vector<String> logs = new Vector<>();

        logs.add("Usuário acessou o sistema");
        logs.add("Pedido 001 criado");
        logs.add("Pagamento aprovado");
        logs.add("Pedido enviado");

        System.out.println("\nLogs de auditoria:");

        for (String log : logs) {
            System.out.println(log);
        }


        // =========================================================
        // MISSÃO 3 - SET, HASHSET E ITERATOR
        // =========================================================

        System.out.println("\n========================================");
        System.out.println("MISSÃO 3 - CUPONS");
        System.out.println("========================================");

        Set<CupomDesconto> conjuntoCupons = new HashSet<>();

        CupomDesconto cupom1 =
                new CupomDesconto("PROMO10", 10);

        CupomDesconto cupom2 =
                new CupomDesconto("PROMO20", 20);

        CupomDesconto cupom3 =
                new CupomDesconto("PROMO10", 50);

        CupomDesconto cupom4 =
                new CupomDesconto("ZERODESCONTO", 0);

        boolean adicionou1 = conjuntoCupons.add(cupom1);
        boolean adicionou2 = conjuntoCupons.add(cupom2);

        // Possui o mesmo código de cupom1.
        boolean adicionou3 = conjuntoCupons.add(cupom3);

        conjuntoCupons.add(cupom4);

        System.out.println("Cupom PROMO10 adicionado: " + adicionou1);
        System.out.println("Cupom PROMO20 adicionado: " + adicionou2);
        System.out.println(
                "Cupom PROMO10 duplicado adicionado: " + adicionou3
        );

        System.out.println("\nCupons cadastrados:");

        for (CupomDesconto cupom : conjuntoCupons) {
            System.out.println(cupom);
        }

        /*
         * O Iterator é utilizado para remover elementos durante
         * a navegação pela coleção.
         *
         * Usar conjuntoCupons.remove() diretamente dentro do
         * foreach poderia gerar ConcurrentModificationException.
         */

        GerenciadorCupons.limparCuponsSemDesconto(conjuntoCupons);

        System.out.println("\nCupons após a limpeza:");

        for (CupomDesconto cupom : conjuntoCupons) {
            System.out.println(cupom);
        }


        // =========================================================
        // MISSÃO 4 - TREESET, COMPARABLE E COMPARATOR
        // =========================================================

        System.out.println("\n========================================");
        System.out.println("MISSÃO 4 - ORDENAÇÃO");
        System.out.println("========================================");

        Produto produto1 =
                new Produto("001", "Notebook", 3500.00);

        Produto produto2 =
                new Produto("002", "Celular", 2500.00);

        Produto produto3 =
                new Produto("003", "Fone de Ouvido", 350.00);

        Produto produto4 =
                new Produto("004", "Teclado", 250.00);

        Produto produto5 =
                new Produto("005", "Monitor", 1200.00);


        /*
         * TreeSet utiliza a ordenação natural definida pelo
         * Comparable da classe Produto.
         *
         * Operações de inserção, busca e remoção possuem
         * complexidade O(log n).
         */

        SortedSet<Produto> catalogoNatural = new TreeSet<>();

        // Produtos adicionados fora da ordem alfabética
        catalogoNatural.add(produto1);
        catalogoNatural.add(produto3);
        catalogoNatural.add(produto5);
        catalogoNatural.add(produto2);
        catalogoNatural.add(produto4);

        System.out.println("Catálogo em ordem alfabética:");

        for (Produto produto : catalogoNatural) {
            System.out.println(produto);
        }


        /*
         * Agora criamos outro TreeSet utilizando um Comparator.
         *
         * Nesse caso, a ordenação será feita pelo preço.
         */

        SortedSet<Produto> catalogoPreco =
                new TreeSet<>(new ComparadorPorPreco());

        catalogoPreco.add(produto1);
        catalogoPreco.add(produto2);
        catalogoPreco.add(produto3);
        catalogoPreco.add(produto4);
        catalogoPreco.add(produto5);

        System.out.println("\nCatálogo ordenado por preço:");

        for (Produto produto : catalogoPreco) {
            System.out.println(produto);
        }


        // =========================================================
        // MISSÃO 5 - HASHMAP E ACESSO POR CHAVE
        // =========================================================

        System.out.println("\n========================================");
        System.out.println("MISSÃO 5 - MAPA DE ESTOQUE");
        System.out.println("========================================");

        Map<String, Produto> mapaEstoque = new HashMap<>();

        /*
         * O código do produto será utilizado como chave.
         *
         * HashMap permite acesso médio O(1) através da chave.
         */

        mapaEstoque.put(produto1.getCodigo(), produto1);
        mapaEstoque.put(produto2.getCodigo(), produto2);
        mapaEstoque.put(produto3.getCodigo(), produto3);
        mapaEstoque.put(produto4.getCodigo(), produto4);
        mapaEstoque.put(produto5.getCodigo(), produto5);

        System.out.println("Produtos cadastrados no estoque:");

        for (Produto produto : mapaEstoque.values()) {
            System.out.println(produto);
        }


        // Consulta através do código de barras
        System.out.println("\nDigite o código do produto para consultar:");

        String codigoBusca = scanner.nextLine();

        // A consulta é feita diretamente pelo get(),
        // sem utilizar laços de repetição.
        Produto produtoEncontrado = mapaEstoque.get(codigoBusca);

        if (produtoEncontrado != null) {

            System.out.println("\nProduto encontrado:");
            System.out.println(produtoEncontrado);

        } else {

            System.out.println("\nProduto não encontrado.");
        }


        // =========================================================
        // KEYSET()
        // =========================================================

        System.out.println("\nChaves registradas no estoque:");

        Set<String> chaves = mapaEstoque.keySet();

        for (String chave : chaves) {
            System.out.println(chave);
        }


        // =========================================================
        // VALUES()
        // =========================================================

        System.out.println("\nProdutos armazenados:");

        Collection<Produto> produtos =
                mapaEstoque.values();

        for (Produto produto : produtos) {
            System.out.println(produto);
        }


        System.out.println("\n========================================");
        System.out.println("PROGRAMA FINALIZADO");
        System.out.println("========================================");

        scanner.close();
    }
}