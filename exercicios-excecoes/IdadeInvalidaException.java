// Jhonatan Carlos Rodrigues Queiroz

/**
 * Exceção personalizada lançada quando a idade informada é inválida
 * (menor que 0 ou maior que 150).
 */
public class IdadeInvalidaException extends RuntimeException {

    public IdadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}
