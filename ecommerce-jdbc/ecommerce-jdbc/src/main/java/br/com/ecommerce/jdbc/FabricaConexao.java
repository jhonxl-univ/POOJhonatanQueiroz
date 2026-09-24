package br.com.ecommerce.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária que implementa o padrão de projeto Factory.
 * Centraliza a lógica de criação/obtenção de conexões físicas com o
 * PostgreSQL, evitando que o restante da aplicação precise conhecer
 * detalhes de driver, URL e credenciais.
 */
public class FabricaConexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/bdecommerce";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    // Construtor privado: classe utilitária, não deve ser instanciada.
    private FabricaConexao() {
    }

    /**
     * Solicita ao ClassLoader da JVM a carga do bytecode do driver JDBC
     * do PostgreSQL e devolve uma conexão ativa com o banco de dados.
     *
     * @return conexão ativa (interface java.sql.Connection, implementada
     *         polimorficamente pelo driver do PostgreSQL)
     * @throws ClassNotFoundException se o driver não estiver no classpath
     * @throws SQLException           se houver falha de rede ou credenciais
     */
    public static Connection obterConexao() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
