package br.com.ecommerce.colecoes;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * MISSÃO — Estrutura legada Hashtable e Enumeration.
 * <p>
 * {@link Hashtable} é uma das classes originais do Java (anterior ao
 * próprio Collections Framework, introduzido no JDK 1.2), mas continua
 * disponível e ainda aparece em código legado e em bibliotecas antigas.
 * Assim como o {@code Vector}, ela é <b>internamente sincronizada</b>
 * (thread-safe), mas isso tem um custo de desempenho em cenários de
 * thread única. Diferente do {@code HashMap}, a Hashtable não aceita
 * chaves nem valores {@code null}.
 * <p>
 * A forma legada de percorrer suas chaves/valores é através da interface
 * {@link Enumeration} — precursora do {@code Iterator} moderno — que
 * expõe apenas dois métodos: {@code hasMoreElements()} e {@code nextElement()}.
 * Note que, diferente do Iterator, a Enumeration não oferece um método
 * {@code remove()}.
 */
public class GerenciadorSessoes {

    private final Hashtable<String, String> sessoesAtivas = new Hashtable<>();

    /**
     * Registra (ou atualiza) uma sessão ativa: chave = identificador do
     * usuário/token, valor = informação da sessão (ex.: horário de login).
     */
    public void abrirSessao(String idUsuario, String informacaoSessao) {
        sessoesAtivas.put(idUsuario, informacaoSessao);
    }

    public void encerrarSessao(String idUsuario) {
        sessoesAtivas.remove(idUsuario);
    }

    public int totalDeSessoesAtivas() {
        return sessoesAtivas.size();
    }

    /**
     * Percorre todas as sessões ativas utilizando exclusivamente a
     * interface legada Enumeration, como pedido pelo exercício.
     */
    public void listarSessoesAtivas() {
        Enumeration<String> chaves = sessoesAtivas.keys();
        while (chaves.hasMoreElements()) {
            String idUsuario = chaves.nextElement();
            String informacaoSessao = sessoesAtivas.get(idUsuario);
            System.out.println("Usuário: " + idUsuario + " | Sessão: " + informacaoSessao);
        }
    }

    /**
     * Mesma varredura, mas sobre os valores (elements()) em vez das
     * chaves (keys()) — demonstra que Enumeration é obtida tanto de
     * keys() quanto de elements().
     */
    public void listarInformacoesDeSessao() {
        Enumeration<String> valores = sessoesAtivas.elements();
        while (valores.hasMoreElements()) {
            String informacaoSessao = valores.nextElement();
            System.out.println("Sessão registrada: " + informacaoSessao);
        }
    }
}
