package br.com.ecommerce.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe responsável por isolar e validar a infraestrutura de conexão,
 * tratando separadamente a ausência do driver (ClassNotFoundException)
 * e falhas de comunicação com o banco (SQLException).
 */
public class TesteConexaoPostgreSQL {

    public static void main(String[] args) {
        Connection conexao = null;
        try {
            conexao = FabricaConexao.obterConexao();
            System.out.println("Conexão estabelecida com sucesso!");

            // Comprova que java.sql.Connection é uma interface realizada
            // polimorficamente pelo driver concreto do PostgreSQL.
            System.out.println("Classe concreta retornada: " + conexao.getClass().getName());

        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do PostgreSQL não encontrado no classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados (rede/credenciais).");
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexão encerrada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
