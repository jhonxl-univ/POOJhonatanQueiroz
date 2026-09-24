package br.com.ecommerce.servico;

import br.com.ecommerce.excecao.EstoqueInsuficienteException;
import br.com.ecommerce.jdbc.FabricaConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Serviço responsável por coordenar operações complexas que envolvem
 * múltiplas tabelas, garantindo a integridade ACID (Atomicidade,
 * Consistência, Isolamento e Durabilidade) do checkout de uma compra:
 * debitar o estoque do produto e registrar o pedido, de forma atômica.
 */
public class ServicoVendaTransacional {

    public boolean processarVenda(String idPedido, String codigoProduto, int quantidadeComprada) {
        Connection conexao = null;
        PreparedStatement selectEstoque = null;
        PreparedStatement updateEstoque = null;
        PreparedStatement insertPedido = null;
        ResultSet rs = null;

        try {
            conexao = FabricaConexao.obterConexao();

            // Desativa o auto-commit padrão do JDBC para agrupar as duas
            // operações (baixa de estoque + registro do pedido) em uma
            // única transação atômica.
            conexao.setAutoCommit(false);

            // 1) Verifica a quantidade disponível em estoque
            selectEstoque = conexao.prepareStatement(
                    "SELECT quantidade_estoque FROM produto WHERE codigo = ? FOR UPDATE");
            selectEstoque.setString(1, codigoProduto);
            rs = selectEstoque.executeQuery();

            if (!rs.next()) {
                throw new EstoqueInsuficienteException("Produto não encontrado: " + codigoProduto);
            }

            int quantidadeDisponivel = rs.getInt("quantidade_estoque");

            // 2) Caso a quantidade seja inferior à solicitada, interrompe o fluxo
            if (quantidadeDisponivel < quantidadeComprada) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto " + codigoProduto +
                                ". Disponível: " + quantidadeDisponivel + ", solicitado: " + quantidadeComprada);
            }

            // 3) Debita a quantidade do estoque
            updateEstoque = conexao.prepareStatement(
                    "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE codigo = ?");
            updateEstoque.setInt(1, quantidadeComprada);
            updateEstoque.setString(2, codigoProduto);
            updateEstoque.executeUpdate();

            // 4) Registra o pedido
            insertPedido = conexao.prepareStatement(
                    "INSERT INTO pedido (id_pedido, codigo_produto, quantidade_comprada, data_pedido) VALUES (?, ?, ?, ?)");
            insertPedido.setString(1, idPedido);
            insertPedido.setString(2, codigoProduto);
            insertPedido.setInt(3, quantidadeComprada);
            insertPedido.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            insertPedido.executeUpdate();

            // 5) Ambos os comandos foram executados sem erros: confirma a gravação
            conexao.commit();
            System.out.println("Venda processada e confirmada com sucesso (commit).");
            return true;

        } catch (EstoqueInsuficienteException | ClassNotFoundException | SQLException e) {
            // 6) Qualquer falha desfaz todas as alterações parciais da transação
            System.err.println("Falha ao processar venda: " + e.getMessage());
            if (conexao != null) {
                try {
                    conexao.rollback();
                    System.err.println("Rollback executado com sucesso.");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;

        } finally {
            try {
                if (rs != null) rs.close();
                if (selectEstoque != null) selectEstoque.close();
                if (updateEstoque != null) updateEstoque.close();
                if (insertPedido != null) insertPedido.close();
                if (conexao != null) {
                    // Restaura o comportamento padrão de auto-commit antes de encerrar
                    conexao.setAutoCommit(true);
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
