package br.com.empresa.excecao;

/**
 * Violação de uma regra de negócio (em geral, uma restrição de integridade
 * referencial traduzida para uma mensagem compreensível ao usuário).
 */
public class RegraNegocioException extends Exception {

    private static final long serialVersionUID = 1L;

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
