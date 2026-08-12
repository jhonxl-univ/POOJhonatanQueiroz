// Jhonatan Carlos Rodrigues Queiroz

/**
 * Exceção personalizada lançada quando se tenta sacar um valor maior
 * que o saldo disponível em uma ContaBancaria.
 */
public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
