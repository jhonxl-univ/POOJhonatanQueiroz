package br.com.empresa.modelo;

/**
 * Utilitário para tratar CPFs: o banco guarda apenas os 11 dígitos;
 * a formatação (000.000.000-00) é só para exibição.
 * Não valida os dígitos verificadores (fora do escopo do exercício).
 */
public final class Cpf {

    private Cpf() {
    }

    /**
     * Remove pontos, traços e espaços e confere se restaram 11 dígitos.
     *
     * @throws IllegalArgumentException se o CPF não tiver 11 dígitos numéricos
     */
    public static String normalizar(String entrada) {
        String digitos = entrada == null ? "" : entrada.replaceAll("[.\\-\\s]", "");
        if (!digitos.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido: informe 11 dígitos numéricos.");
        }
        return digitos;
    }

    public static String formatar(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }
}
