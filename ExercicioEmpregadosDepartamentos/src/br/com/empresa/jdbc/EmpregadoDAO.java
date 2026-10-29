package br.com.empresa.jdbc;

import br.com.empresa.excecao.RegraNegocioException;
import br.com.empresa.modelo.Cpf;
import br.com.empresa.modelo.Empregado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de empregados. As consultas de leitura usam JOIN com departamento
 * para trazer também o nome do departamento. Violações de integridade
 * referencial (SQLState 23503/23505) são traduzidas em regras de negócio.
 */
public class EmpregadoDAO {

    private static final String SQLSTATE_UNIQUE = "23505";
    private static final String SQLSTATE_FK = "23503";

    private static final String SELECT_COM_DEPARTAMENTO =
            "SELECT e.cpf, e.nome, e.departamento_sigla, d.nome AS departamento_nome "
          + "FROM empregado e "
          + "JOIN departamento d ON d.sigla = e.departamento_sigla ";

    public void inserir(Empregado empregado) throws SQLException, RegraNegocioException {
        String sql = "INSERT INTO empregado (cpf, nome, departamento_sigla) VALUES (?, ?, ?)";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, empregado.getCpf());
            comando.setString(2, empregado.getNome());
            comando.setString(3, empregado.getDepartamentoSigla());
            comando.executeUpdate();
        } catch (SQLException e) {
            if (SQLSTATE_UNIQUE.equals(e.getSQLState())) {
                throw new RegraNegocioException("Já existe um empregado com o CPF "
                        + Cpf.formatar(empregado.getCpf()) + ".");
            }
            if (SQLSTATE_FK.equals(e.getSQLState())) {
                throw new RegraNegocioException("O departamento '" + empregado.getDepartamentoSigla()
                        + "' não existe. Todo empregado deve estar alocado em um departamento existente.");
            }
            throw e;
        }
    }

    public List<Empregado> listarTodos() throws SQLException {
        return consultar(SELECT_COM_DEPARTAMENTO + "ORDER BY e.nome", null);
    }

    public List<Empregado> listarPorDepartamento(String sigla) throws SQLException {
        return consultar(SELECT_COM_DEPARTAMENTO + "WHERE e.departamento_sigla = ? ORDER BY e.nome", sigla);
    }

    /** @return o empregado, ou null se o CPF não existir. */
    public Empregado buscarPorCpf(String cpf) throws SQLException {
        List<Empregado> resultado = consultar(SELECT_COM_DEPARTAMENTO + "WHERE e.cpf = ?", cpf);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public boolean alterarNome(String cpf, String novoNome) throws SQLException {
        String sql = "UPDATE empregado SET nome = ? WHERE cpf = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, novoNome);
            comando.setString(2, cpf);
            return comando.executeUpdate() > 0;
        }
    }

    /** Transfere o empregado para outro departamento (que precisa existir). */
    public boolean transferir(String cpf, String novaSigla) throws SQLException, RegraNegocioException {
        String sql = "UPDATE empregado SET departamento_sigla = ? WHERE cpf = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, novaSigla);
            comando.setString(2, cpf);
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            if (SQLSTATE_FK.equals(e.getSQLState())) {
                throw new RegraNegocioException("O departamento '" + novaSigla + "' não existe.");
            }
            throw e;
        }
    }

    /**
     * Exclui o empregado, desde que ele não seja gerente de nenhum departamento.
     *
     * @return true se excluiu; false se o CPF não existe.
     */
    public boolean excluir(String cpf) throws SQLException, RegraNegocioException {
        try (Connection conexao = FabricaConexao.obterConexao()) {
            String sqlGerente = "SELECT sigla FROM departamento WHERE gerente_cpf = ?";
            try (PreparedStatement verificacao = conexao.prepareStatement(sqlGerente)) {
                verificacao.setString(1, cpf);
                try (ResultSet rs = verificacao.executeQuery()) {
                    if (rs.next()) {
                        throw new RegraNegocioException("Este empregado é gerente do departamento '"
                                + rs.getString("sigla") + "'. Remova-o da gerência antes de excluí-lo.");
                    }
                }
            }
            try (PreparedStatement comando = conexao.prepareStatement("DELETE FROM empregado WHERE cpf = ?")) {
                comando.setString(1, cpf);
                return comando.executeUpdate() > 0;
            }
        }
    }

    private List<Empregado> consultar(String sql, String parametro) throws SQLException {
        List<Empregado> empregados = new ArrayList<>();
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            if (parametro != null) {
                comando.setString(1, parametro);
            }
            try (ResultSet rs = comando.executeQuery()) {
                while (rs.next()) {
                    Empregado e = new Empregado(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("departamento_sigla"));
                    e.setDepartamentoNome(rs.getString("departamento_nome"));
                    empregados.add(e);
                }
            }
        }
        return empregados;
    }
}
