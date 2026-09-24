package br.com.ecommerce.excecao;

/**
 * Exceção de regra de negócio, lançada deliberadamente quando a quantidade
 * de estoque disponível é inferior à quantidade solicitada em uma venda.
 * Sendo uma RuntimeException, interrompe imediatamente o fluxo de execução
 * e sinaliza ao serviço transacional que um rollback deve ser realizado.
 */
public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
