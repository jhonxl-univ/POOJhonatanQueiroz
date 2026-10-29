package br.com.empresa.jdbc;

import br.com.empresa.excecao.RegraNegocioException;
import br.com.empresa.modelo.Cpf;
import br.com.empresa.modelo.Departamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de departamentos e gerenciamento da gerência. As consultas usam
 * LEFT JOIN com empregado para exibir o nome do gerente — o LEFT JOIN
 * garante que departamentos sem gerente (gerente_cpf NULL) também apareçam.
 */
public class DepartamentoDAO {

    private static final String SQLSTATE_UNIQUE = "23505";
    private static final String SQLSTATE_FK = "23503";

    private static final String SELECT_COM_GERENTE =
            "SELECT d.sigla, d.nome, d.gerente_cpf, g.nome AS gerente_nome "
          + "FROM departamento d "
          + "LEFT JOIN empregado g ON g.cpf = d.gerente_cpf ";

    /** Cadastra o departamento sem gerente (o gerente é alocado depois). */
    public void inserir(Departamento departamento) throws SQLException, RegraNegocioException {
        String sql = "INSERT INTO departamento (sigla, nome) VALUES (?, ?)";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, departamento.getSigla());
            comando.setString(2, departamento.getNome());
            comando.executeUpdate();
        } catch (SQLException e) {
            if (SQLSTATE_UNIQUE.equals(e.getSQLState())) {
                throw new RegraNegocioException("Já existe um departamento com a sigla '"
                        + departamento.getSigla() + "'.");
            }
            throw e;
        }
    }

    public List<Departamento> listarTodos() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(SELECT_COM_GERENTE + "ORDER BY d.sigla");
             ResultSet rs = comando.executeQuery()) {
            while (rs.next()) {
                departamentos.add(mapear(rs));
            }
        }
        return departamentos;
    }

    /** @return o departamento, ou null se a sigla não existir. */
    public Departamento buscarPorSigla(String sigla) throws SQLException {
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(SELECT_COM_GERENTE + "WHERE d.sigla = ?")) {
            comando.setString(1, sigla);
            try (ResultSet rs = comando.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public boolean alterarNome(String sigla, String novoNome) throws SQLException {
        String sql = "UPDATE departamento SET nome = ? WHERE sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, novoNome);
            comando.setString(2, sigla);
            return comando.executeUpdate() > 0;
        }
    }

    /**
     * Define o gerente do departamento. O CPF precisa pertencer a um
     * empregado existente (garantido pela FK fk_departamento_gerente).
     *
     * @return true se atualizou; false se a sigla do departamento não existe.
     */
    public boolean alocarGerente(String sigla, String cpfGerente) throws SQLException, RegraNegocioException {
        String sql = "UPDATE departamento SET gerente_cpf = ? WHERE sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, cpfGerente);
            comando.setString(2, sigla);
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            if (SQLSTATE_FK.equals(e.getSQLState())) {
                throw new RegraNegocioException("Não existe empregado com o CPF "
                        + Cpf.formatar(cpfGerente) + ". O gerente deve ser um empregado cadastrado.");
            }
            throw e;
        }
    }

    /** Deixa o departamento sem gerente (gerente_cpf = NULL). */
    public boolean removerGerente(String sigla) throws SQLException {
        String sql = "UPDATE departamento SET gerente_cpf = NULL WHERE sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, sigla);
            return comando.executeUpdate() > 0;
        }
    }

    /**
     * Exclui o departamento, desde que não tenha empregados alocados.
     *
     * @return true se excluiu; false se a sigla não existe.
     */
    public boolean excluir(String sigla) throws SQLException, RegraNegocioException {
        try (Connection conexao = FabricaConexao.obterConexao()) {
            String sqlContagem = "SELECT COUNT(*) FROM empregado WHERE departamento_sigla = ?";
            try (PreparedStatement contagem = conexao.prepareStatement(sqlContagem)) {
                contagem.setString(1, sigla);
                try (ResultSet rs = contagem.executeQuery()) {
                    rs.next();
                    int total = rs.getInt(1);
                    if (total > 0) {
                        throw new RegraNegocioException("O departamento '" + sigla + "' ainda possui "
                                + total + " empregado(s). Transfira ou exclua os empregados antes.");
                    }
                }
            }
            try (PreparedStatement comando = conexao.prepareStatement("DELETE FROM departamento WHERE sigla = ?")) {
                comando.setString(1, sigla);
                return comando.executeUpdate() > 0;
            }
        }
    }

    private Departamento mapear(ResultSet rs) throws SQLException {
        Departamento d = new Departamento(rs.getString("sigla"), rs.getString("nome"));
        d.setGerenteCpf(rs.getString("gerente_cpf"));   // null quando não há gerente
        d.setGerenteNome(rs.getString("gerente_nome"));
        return d;
    }
}
