package br.com.ecommerce.colecoes;

import java.util.LinkedList;
import java.util.Queue;

/**
 * MISSÃO — Contrato formal da interface Queue.
 * <p>
 * A fila de pedidos é modelada estritamente através da interface
 * {@link Queue}, implementada por {@link LinkedList}. Por contrato,
 * uma fila segue a disciplina FIFO (First-In, First-Out): o primeiro
 * pedido a entrar é o primeiro a ser atendido.
 * <p>
 * Diferente de {@code add()}/{@code remove()}/{@code element()} (que lançam
 * exceção em situações-limite, como fila vazia ou cheia), o exercício usa
 * exclusivamente o trio "seguro" do contrato de fila:
 * <ul>
 *     <li>{@code offer(e)} — insere no final da fila; retorna {@code false} em vez de lançar exceção se a inserção falhar;</li>
 *     <li>{@code peek()} — consulta o elemento da frente sem removê-lo; retorna {@code null} se a fila estiver vazia;</li>
 *     <li>{@code poll()} — remove e devolve o elemento da frente; retorna {@code null} se a fila estiver vazia.</li>
 * </ul>
 */
public class FilaDePedidos {

    private final Queue<String> fila = new LinkedList<>();

    /**
     * Enfileira um novo pedido ao final da fila.
     */
    public boolean novoPedido(String idPedido) {
        return fila.offer(idPedido);
    }

    /**
     * Consulta qual é o próximo pedido a ser atendido, sem removê-lo da fila.
     */
    public String proximoPedido() {
        return fila.peek();
    }

    /**
     * Remove e devolve o pedido da frente da fila — simula o atendimento
     * (despacho) do pedido mais antigo.
     */
    public String atenderPedido() {
        return fila.poll();
    }

    public boolean estaVazia() {
        return fila.isEmpty();
    }

    public int tamanho() {
        return fila.size();
    }
}
