package br.com.empresa.modelo;

/**
 * Entidade Departamento. Possui sigla e nome e, opcionalmente, um gerente
 * (um empregado, identificado pelo CPF). O nome do gerente só é preenchido
 * nas consultas com LEFT JOIN (é informação de exibição).
 */
public class Departamento {

    private String sigla;
    private String nome;
    private String gerenteCpf;
    private String gerenteNome;

    public Departamento() {
    }

    public Departamento(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getGerenteCpf() { return gerenteCpf; }
    public void setGerenteCpf(String gerenteCpf) { this.gerenteCpf = gerenteCpf; }

    public String getGerenteNome() { return gerenteNome; }
    public void setGerenteNome(String gerenteNome) { this.gerenteNome = gerenteNome; }

    @Override
    public String toString() {
        String gerente = gerenteCpf == null
                ? "(sem gerente)"
                : gerenteNome + " (" + Cpf.formatar(gerenteCpf) + ")";
        return String.format("Sigla: %s | Nome: %s | Gerente: %s", sigla, nome, gerente);
    }
}
