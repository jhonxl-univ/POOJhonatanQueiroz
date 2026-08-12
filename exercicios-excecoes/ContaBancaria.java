// Jhonatan Carlos Rodrigues Queiroz

/**
 * Classe que representa uma conta bancária simples, com saldo e
 * operação de saque que lança SaldoInsuficienteException quando o
 * valor solicitado é maior que o saldo disponível.
 */
public class ContaBancaria {

    private double saldo;

    public ContaBancaria(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente! Saldo atual: " + saldo + ", valor solicitado: " + valor);
        }
        saldo -= valor;
    }
}
