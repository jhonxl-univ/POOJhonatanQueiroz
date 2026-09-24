package br.com.ecommerce.ui;

import br.com.ecommerce.jdbc.ProdutoDAO;
import br.com.ecommerce.jdbc.RecursosAvancadosDAO;
import br.com.ecommerce.modelo.Produto;
import br.com.ecommerce.servico.ServicoVendaTransacional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Ponto de entrada da aplicação. Representa a camada de visualização,
 * interagindo exclusivamente com o terminal: orquestra as classes DAO
 * e de Serviço, coleta entradas do usuário via Scanner e invoca os
 * métodos correspondentes de acordo com a opção escolhida no menu.
 */
public class MainEcommerce {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final ServicoVendaTransacional servicoVenda = new ServicoVendaTransacional();
    private static final RecursosAvancadosDAO recursosAvancadosDAO = new RecursosAvancadosDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> inserirProduto();
                case 2 -> listarProdutos();
                case 3 -> buscarProduto();
                case 4 -> atualizarPreco();
                case 5 -> excluirProduto();
                case 6 -> processarVenda();
                case 7 -> recursosAvancadosDAO.demonstrarCursorRolaVel();
                case 8 -> executarProcedure();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("========================================");
        System.out.println(" SISTEMA DE GESTÃO LOGÍSTICA E E-COMMERCE");
        System.out.println("========================================");
        System.out.println("1 - Inserir produto");
        System.out.println("2 - Listar todos os produtos");
        System.out.println("3 - Buscar produto por código");
        System.out.println("4 - Atualizar preço de um produto");
        System.out.println("5 - Excluir produto");
        System.out.println("6 - Processar venda (transação)");
        System.out.println("7 - Demonstrar cursor rolável");
        System.out.println("8 - Consultar saldo de estoque (stored procedure)");
        System.out.println("0 - Sair");
    }

    private static void inserirProduto() {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        BigDecimal preco = new BigDecimal(lerTexto("Preço: "));
        int quantidade = lerInteiro("Quantidade em estoque: ");

        Produto produto = new Produto(codigo, nome, preco, quantidade);
        boolean sucesso = produtoDAO.inserir(produto);
        System.out.println(sucesso ? "Produto inserido com sucesso!" : "Falha ao inserir produto.");
    }

    private static void listarProdutos() {
        List<Produto> produtos = produtoDAO.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        produtos.forEach(System.out::println);
    }

    private static void buscarProduto() {
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        Produto produto = produtoDAO.buscarPorCodigo(codigo);
        System.out.println(produto != null ? produto : "Produto não encontrado.");
    }

    private static void atualizarPreco() {
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        BigDecimal novoPreco = new BigDecimal(lerTexto("Novo preço: "));
        boolean sucesso = produtoDAO.atualizarPreco(codigo, novoPreco);
        System.out.println(sucesso ? "Preço atualizado com sucesso!" : "Falha ao atualizar preço.");
    }

    private static void excluirProduto() {
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        boolean sucesso = produtoDAO.excluir(codigo);
        System.out.println(sucesso ? "Produto excluído com sucesso!" : "Falha ao excluir produto.");
    }

    private static void processarVenda() {
        System.out.print("ID do pedido: ");
        String idPedido = scanner.nextLine();
        System.out.print("Código do produto: ");
        String codigoProduto = scanner.nextLine();
        int quantidade = lerInteiro("Quantidade comprada: ");

        servicoVenda.processarVenda(idPedido, codigoProduto, quantidade);
    }

    private static void executarProcedure() {
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        recursosAvancadosDAO.executarProcedureSaldo(codigo);
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Valor inválido. " + mensagem);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // consome a quebra de linha pendente
        return valor;
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}
