package br.com.empresa.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Factory de conexões com o PostgreSQL. Centraliza URL, usuário e senha.
 * Ajuste SENHA caso a senha do seu usuário "postgres" seja diferente.
 */
public class FabricaConexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/bddepartamentos";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    private FabricaConexao() {
    }

    public static Connection obterConexao() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "Driver JDBC do PostgreSQL não encontrado no classpath (confira a pasta lib/).", e);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
