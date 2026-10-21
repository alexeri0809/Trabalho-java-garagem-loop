package com.example;

/**
 * Um nó da lista ligada circular. Cada nó representa uma vaga da garagem.
 */
public class No {

    private Carro conteudo;
    private No next;

    /**
     * @param conteudo o carro da vaga, ou null se a vaga estiver vazia
     */
    public No(Carro conteudo) {
        this.conteudo = conteudo;
    }

    /** @return o carro desta vaga, ou null se for o Vazio */
    public Carro getConteudo() {
        return conteudo;
    }

    /** @param conteudo o novo conteúdo da vaga (null para o Vazio) */
    public void setConteudo(Carro conteudo) {
        this.conteudo = conteudo;
    }

    /** @return o próximo nó da lista */
    public No getNext() {
        return next;
    }

    /** @param next o próximo nó da lista */
    public void setNext(No next) {
        this.next = next;
    }

    /** @return true se a vaga estiver vazia */
    public boolean isVazio() {
        return conteudo == null;
    }
}
