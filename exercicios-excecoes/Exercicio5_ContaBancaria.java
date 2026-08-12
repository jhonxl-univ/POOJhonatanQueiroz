// Jhonatan Carlos Rodrigues Queiroz

/**
 * 5. Cadastro de Conta Bancária
 *
 * Testa a classe ContaBancaria fazendo um saque válido e um saque que
 * estoura o saldo, tratando SaldoInsuficienteException.
 */
public class Exercicio5_ContaBancaria {

    public static void main(String[] args) {
        ContaBancaria conta = new ContaBancaria(1000.0);

        System.out.println("Saldo inicial: " + conta.getSaldo());

        try {
            System.out.println("\nTentando sacar R$ 300,00 (saque válido)...");
            conta.sacar(300.0);
            System.out.println("Saque realizado com sucesso!");
            System.out.println("Saldo atual: " + conta.getSaldo());

        } catch (SaldoInsuficienteException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("Fim da operação");
        }

        try {
            System.out.println("\nTentando sacar R$ 5000,00 (saque que estoura o saldo)...");
            conta.sacar(5000.0);
            System.out.println("Saque realizado com sucesso!");
            System.out.println("Saldo atual: " + conta.getSaldo());

        } catch (SaldoInsuficienteException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("Fim da operação");
        }
    }
}
