package br.com.ecommerce.jdbc;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * DAO que demonstra recursos avançados do JDBC: cursores roláveis
 * (navegação bidirecional pelo ResultSet) e execução de rotinas
 * (stored procedures) compiladas no próprio servidor PostgreSQL.
 */
public class RecursosAvancadosDAO {

    /**
     * Demonstra a navegação não linear (rolável) de um ResultSet,
     * possível ao criar o PreparedStatement com TYPE_SCROLL_INSENSITIVE.
     */
    public void demonstrarCursorRolaVel() {
        String sql = "SELECT codigo, nome, preco FROM produto ORDER BY preco ASC";
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet rs = null;

        try {
            conexao = FabricaConexao.obterConexao();
            comando = conexao.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = comando.executeQuery();

            // Vai para o registro mais caro (último, já que a ordenação é ASC)
            if (rs.last()) {
                System.out.println("Produto mais caro (linha " + rs.getRow() + "): " +
                        rs.getString("nome") + " - R$ " + rs.getBigDecimal("preco"));
            }

            // Retrocede para a tupla imediatamente anterior
            if (rs.previous()) {
                System.out.println("Produto anterior (linha " + rs.getRow() + "): " +
                        rs.getString("nome") + " - R$ " + rs.getBigDecimal("preco"));
            }

            // Salta diretamente para o registro mais barato (primeiro)
            if (rs.first()) {
                System.out.println("Produto mais barato (linha " + rs.getRow() + "): " +
                        rs.getString("nome") + " - R$ " + rs.getBigDecimal("preco"));
            }

            // Posiciona o cursor diretamente na segunda tupla retornada
            if (rs.absolute(2)) {
                System.out.println("Segundo produto da lista (linha " + rs.getRow() + "): " +
                        rs.getString("nome") + " - R$ " + rs.getBigDecimal("preco"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            fechar(conexao, comando, rs);
        }
    }

    /**
     * Executa a stored procedure sp_calcular_saldo_estoque através da
     * interface CallableStatement, registrando os parâmetros de saída
     * (OUT) antes da chamada e recuperando os valores após a execução.
     */
    public void executarProcedureSaldo(String codigoProduto) {
        Connection conexao = null;
        CallableStatement callStmt = null;

        try {
            conexao = FabricaConexao.obterConexao();
            callStmt = conexao.prepareCall("{call sp_calcular_saldo_estoque(?, ?, ?)}");

            // Parâmetro de entrada (IN)
            callStmt.setString(1, codigoProduto);

            // Registro dos parâmetros de saída (OUT)
            callStmt.registerOutParameter(2, Types.INTEGER);
            callStmt.registerOutParameter(3, Types.NUMERIC);

            callStmt.execute();

            int quantidade = callStmt.getInt(2);
            BigDecimal totalReais = callStmt.getBigDecimal(3);

            System.out.println("Saldo do produto " + codigoProduto + ": " + quantidade + " unidade(s)");
            System.out.println("Patrimônio monetário em estoque: R$ " + totalReais);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            fechar(conexao, callStmt, null);
        }
    }

    private void fechar(Connection conexao, PreparedStatement comando, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (comando != null) comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conexao != null) conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
