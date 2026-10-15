package com.example;

/**
 * Um nó da lista ligada circular. Cada nó representa uma vaga da garagem.
 */
public class Nó {

    private Carro conteudo; // carro estacionado, ou null para representar o Vazio
    private Nó next;        // próximo nó da lista

    /**
     * Cria um nó.
     *
     * @param conteudo o carro da vaga, ou null se a vaga estiver vazia
     */
    public Nó(Carro conteudo) {
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
    public Nó getNext() {
        return next;
    }

    /** @param next o próximo nó da lista */
    public void setNext(Nó next) {
        this.next = next;
    }

    /** @return true se a vaga estiver vazia */
    public boolean isVazio() {
        return conteudo == null;
    }
}