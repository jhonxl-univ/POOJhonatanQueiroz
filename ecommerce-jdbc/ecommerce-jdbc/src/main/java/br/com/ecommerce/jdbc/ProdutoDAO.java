package br.com.ecommerce.jdbc;

import br.com.ecommerce.modelo.Produto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) responsável por isolar todo o código SQL
 * relacionado à tabela "produto" do restante da aplicação.
 * Concentra as operações de criação, leitura, atualização e exclusão (CRUD).
 */
public class ProdutoDAO {

    /**
     * Insere um novo produto no catálogo utilizando PreparedStatement,
     * prevenindo ataques de SQL Injection através de marcadores posicionais.
     */
    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produto (codigo, nome, preco, quantidade_estoque) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = FabricaConexao.obterConexao();
            comando = conexao.prepareStatement(sql);

            comando.setString(1, produto.getCodigo());
            comando.setString(2, produto.getNome());
            // setBigDecimal garante a precisão monetária compatível com o tipo NUMERIC do SQL
            comando.setBigDecimal(3, produto.getPreco());
            comando.setInt(4, produto.getQuantidadeEstoque());

            int linhasAfetadas = comando.executeUpdate();
            return linhasAfetadas > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            fechar(conexao, comando, null);
        }
    }

    /**
     * Atualiza o preço de um produto existente, identificado pelo código.
     */
    public boolean atualizarPreco(String codigo, BigDecimal novoPreco) {
        String sql = "UPDATE produto SET preco = ? WHERE codigo = ?";
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = FabricaConexao.obterConexao();
            comando = conexao.prepareStatement(sql);

            comando.setBigDecimal(1, novoPreco);
            comando.setString(2, codigo);

            int linhasAfetadas = comando.executeUpdate();
            return linhasAfetadas > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            fechar(conexao, comando, null);
        }
    }

    /**
     * Remove um produto do catálogo a partir do código informado.
     */
    public boolean excluir(String codigo) {
        String sql = "DELETE FROM produto WHERE codigo = ?";
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = FabricaConexao.obterConexao();
            comando = conexao.prepareStatement(sql);

            comando.setString(1, codigo);

            int linhasAfetadas = comando.executeUpdate();
            return linhasAfetadas > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            fechar(conexao, comando, null);
        }
    }

    /**
     * Lista todos os produtos cadastrados, ordenados por nome.
     * Percorre o cursor (ResultSet) sequencialmente com while(rs.next()),
     * extraindo atributos alternadamente por índice posicional e por nome de coluna.
     */
    public List<Produto> listarTodos() {
        String sql = "SELECT codigo, nome, preco, quantidade_estoque FROM produto ORDER BY nome ASC";
        List<Produto> produtos = new ArrayList<>();

        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet rs = null;
        try {
            conexao = FabricaConexao.obterConexao();
            comando = conexao.prepareStatement(sql);
            rs = comando.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setCodigo(rs.getString(1));                    // acesso por índice posicional
                produto.setNome(rs.getString("nome"));                 // acesso por nome de coluna
                produto.setPreco(rs.getBigDecimal("preco"));           // acesso por nome de coluna
                produto.setQuantidadeEstoque(rs.getInt(4));            // acesso por índice posicional
                produtos.add(produto);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            fechar(conexao, comando, rs);
        }

        return produtos;
    }

    /**
     * Busca um único produto pelo código.
     *
     * @return o produto populado, ou null caso rs.next() retorne false.
     */
    public Produto buscarPorCodigo(String codigo) {
        String sql = "SELECT codigo, nome, preco, quantidade_estoque FROM produto WHERE codigo = ?";
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet rs = null;
        try {
            conexao = FabricaConexao.obterConexao();
            comando = conexao.prepareStatement(sql);
            comando.setString(1, codigo);
            rs = comando.executeQuery();

            if (rs.next()) {
                Produto produto = new Produto();
                produto.setCodigo(rs.getString("codigo"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                return produto;
            }
            return null;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            fechar(conexao, comando, rs);
        }
    }

    /**
     * Fecha os recursos alocados em cascata: primeiro o ResultSet, depois o
     * PreparedStatement e por fim a Connection — evitando vazamento de
     * conexões com o banco de dados.
     */
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
