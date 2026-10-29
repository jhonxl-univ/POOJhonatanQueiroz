package br.com.empresa.app;

import br.com.empresa.excecao.RegraNegocioException;
import br.com.empresa.jdbc.DepartamentoDAO;
import br.com.empresa.jdbc.EmpregadoDAO;
import br.com.empresa.modelo.Cpf;
import br.com.empresa.modelo.Departamento;
import br.com.empresa.modelo.Empregado;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Menu principal do sistema de gestão de empregados e departamentos.
 * Camada de visualização: só conversa com o usuário (Scanner) e delega
 * o acesso ao banco para os DAOs.
 */
public class MainGestaoEmpresa {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EmpregadoDAO empregadoDAO = new EmpregadoDAO();
    private static final DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    public static void main(String[] args) {
        String opcao;
        do {
            System.out.println();
            System.out.println("========================================");
            System.out.println("   GESTÃO DE EMPREGADOS E DEPARTAMENTOS");
            System.out.println("========================================");
            System.out.println("1 - Empregados");
            System.out.println("2 - Departamentos");
            System.out.println("0 - Sair");
            opcao = lerTexto("Opção: ");

            switch (opcao) {
                case "1" -> menuEmpregados();
                case "2" -> menuDepartamentos();
                case "0" -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    // ------------------------------------------------------------------
    // Menu de empregados
    // ------------------------------------------------------------------
    private static void menuEmpregados() {
        String opcao;
        do {
            System.out.println();
            System.out.println("--- EMPREGADOS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar todos");
            System.out.println("3 - Buscar por CPF");
            System.out.println("4 - Alterar nome");
            System.out.println("5 - Transferir de departamento");
            System.out.println("6 - Excluir");
            System.out.println("7 - Listar por departamento");
            System.out.println("0 - Voltar");
            opcao = lerTexto("Opção: ");

            try {
                switch (opcao) {
                    case "1" -> cadastrarEmpregado();
                    case "2" -> imprimirLista(empregadoDAO.listarTodos(), "Nenhum empregado cadastrado.");
                    case "3" -> buscarEmpregado();
                    case "4" -> alterarNomeEmpregado();
                    case "5" -> transferirEmpregado();
                    case "6" -> excluirEmpregado();
                    case "7" -> listarEmpregadosPorDepartamento();
                    case "0" -> { }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RegraNegocioException e) {
                System.out.println("[Regra de negócio] " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[Entrada inválida] " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("[Erro de banco de dados] " + e.getMessage());
            }
        } while (!opcao.equals("0"));
    }

    private static void cadastrarEmpregado() throws SQLException, RegraNegocioException {
        String cpf = Cpf.normalizar(lerTexto("CPF: "));
        String nome = lerObrigatorio("Nome: ");
        String sigla = lerSigla("Sigla do departamento: ");
        empregadoDAO.inserir(new Empregado(cpf, nome, sigla));
        System.out.println("Empregado cadastrado com sucesso!");
    }

    private static void buscarEmpregado() throws SQLException {
        String cpf = Cpf.normalizar(lerTexto("CPF: "));
        Empregado e = empregadoDAO.buscarPorCpf(cpf);
        System.out.println(e != null ? e : "Empregado não encontrado.");
    }

    private static void alterarNomeEmpregado() throws SQLException {
        String cpf = Cpf.normalizar(lerTexto("CPF: "));
        String nome = lerObrigatorio("Novo nome: ");
        System.out.println(empregadoDAO.alterarNome(cpf, nome)
                ? "Nome alterado com sucesso!" : "Empregado não encontrado.");
    }

    private static void transferirEmpregado() throws SQLException, RegraNegocioException {
        String cpf = Cpf.normalizar(lerTexto("CPF: "));
        String sigla = lerSigla("Sigla do novo departamento: ");
        System.out.println(empregadoDAO.transferir(cpf, sigla)
                ? "Empregado transferido com sucesso!" : "Empregado não encontrado.");
    }

    private static void excluirEmpregado() throws SQLException, RegraNegocioException {
        String cpf = Cpf.normalizar(lerTexto("CPF: "));
        System.out.println(empregadoDAO.excluir(cpf)
                ? "Empregado excluído com sucesso!" : "Empregado não encontrado.");
    }

    private static void listarEmpregadosPorDepartamento() throws SQLException {
        String sigla = lerSigla("Sigla do departamento: ");
        imprimirLista(empregadoDAO.listarPorDepartamento(sigla),
                "Nenhum empregado neste departamento (ou departamento inexistente).");
    }

    // ------------------------------------------------------------------
    // Menu de departamentos
    // ------------------------------------------------------------------
    private static void menuDepartamentos() {
        String opcao;
        do {
            System.out.println();
            System.out.println("--- DEPARTAMENTOS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar todos");
            System.out.println("3 - Buscar por sigla");
            System.out.println("4 - Alterar nome");
            System.out.println("5 - Alocar gerente");
            System.out.println("6 - Remover gerente");
            System.out.println("7 - Excluir");
            System.out.println("0 - Voltar");
            opcao = lerTexto("Opção: ");

            try {
                switch (opcao) {
                    case "1" -> cadastrarDepartamento();
                    case "2" -> imprimirLista(departamentoDAO.listarTodos(), "Nenhum departamento cadastrado.");
                    case "3" -> buscarDepartamento();
                    case "4" -> alterarNomeDepartamento();
                    case "5" -> alocarGerente();
                    case "6" -> removerGerente();
                    case "7" -> excluirDepartamento();
                    case "0" -> { }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RegraNegocioException e) {
                System.out.println("[Regra de negócio] " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[Entrada inválida] " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("[Erro de banco de dados] " + e.getMessage());
            }
        } while (!opcao.equals("0"));
    }

    private static void cadastrarDepartamento() throws SQLException, RegraNegocioException {
        String sigla = lerSigla("Sigla: ");
        String nome = lerObrigatorio("Nome: ");
        departamentoDAO.inserir(new Departamento(sigla, nome));
        System.out.println("Departamento cadastrado com sucesso!");
    }

    private static void buscarDepartamento() throws SQLException {
        Departamento d = departamentoDAO.buscarPorSigla(lerSigla("Sigla: "));
        System.out.println(d != null ? d : "Departamento não encontrado.");
    }

    private static void alterarNomeDepartamento() throws SQLException {
        String sigla = lerSigla("Sigla: ");
        String nome = lerObrigatorio("Novo nome: ");
        System.out.println(departamentoDAO.alterarNome(sigla, nome)
                ? "Nome alterado com sucesso!" : "Departamento não encontrado.");
    }

    private static void alocarGerente() throws SQLException, RegraNegocioException {
        String sigla = lerSigla("Sigla do departamento: ");
        String cpf = Cpf.normalizar(lerTexto("CPF do gerente: "));
        System.out.println(departamentoDAO.alocarGerente(sigla, cpf)
                ? "Gerente alocado com sucesso!" : "Departamento não encontrado.");
    }

    private static void removerGerente() throws SQLException {
        String sigla = lerSigla("Sigla do departamento: ");
        System.out.println(departamentoDAO.removerGerente(sigla)
                ? "Gerente removido (departamento agora está sem gerente)." : "Departamento não encontrado.");
    }

    private static void excluirDepartamento() throws SQLException, RegraNegocioException {
        String sigla = lerSigla("Sigla: ");
        System.out.println(departamentoDAO.excluir(sigla)
                ? "Departamento excluído com sucesso!" : "Departamento não encontrado.");
    }

    // ------------------------------------------------------------------
    // Utilitários de entrada/saída
    // ------------------------------------------------------------------
    private static <T> void imprimirLista(List<T> itens, String mensagemVazia) {
        if (itens.isEmpty()) {
            System.out.println(mensagemVazia);
        } else {
            itens.forEach(System.out::println);
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "0";
    }

    private static String lerObrigatorio(String mensagem) {
        String valor = lerTexto(mensagem);
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("O campo não pode ficar vazio.");
        }
        return valor;
    }

    /** Siglas são guardadas em maiúsculas (até 10 caracteres). */
    private static String lerSigla(String mensagem) {
        String sigla = lerObrigatorio(mensagem).toUpperCase();
        if (sigla.length() > 10) {
            throw new IllegalArgumentException("A sigla deve ter no máximo 10 caracteres.");
        }
        return sigla;
    }
}
