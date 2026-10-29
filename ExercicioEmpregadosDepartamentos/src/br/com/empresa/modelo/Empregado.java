package br.com.empresa.modelo;

/**
 * Entidade Empregado. Todo empregado tem CPF e nome e está alocado
 * em exatamente um departamento. O nome do departamento só é preenchido
 * nas consultas com JOIN (é informação de exibição).
 */
public class Empregado {

    private String cpf;
    private String nome;
    private String departamentoSigla;
    private String departamentoNome;

    public Empregado() {
    }

    public Empregado(String cpf, String nome, String departamentoSigla) {
        this.cpf = cpf;
        this.nome = nome;
        this.departamentoSigla = departamentoSigla;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDepartamentoSigla() { return departamentoSigla; }
    public void setDepartamentoSigla(String departamentoSigla) { this.departamentoSigla = departamentoSigla; }

    public String getDepartamentoNome() { return departamentoNome; }
    public void setDepartamentoNome(String departamentoNome) { this.departamentoNome = departamentoNome; }

    @Override
    public String toString() {
        String depto = departamentoNome != null
                ? departamentoSigla + " - " + departamentoNome
                : departamentoSigla;
        return String.format("CPF: %s | Nome: %s | Departamento: %s",
                Cpf.formatar(cpf), nome, depto);
    }
}
