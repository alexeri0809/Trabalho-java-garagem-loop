package com.example;

/**
 * Lista ligada circular de vagas. O último nó aponta de volta para o primeiro.
 * Uma vaga com conteúdo null representa o Vazio.
 */
public class ListaCircular {

    private No inicio;
    private No fim;
    private int tamanho;

    /**
     * Acrescenta um carro no fim da lista.
     *
     * @param carro o carro a acrescentar (não pode ser null)
     * @throws IllegalArgumentException se o carro for null
     */
    public void adicionarCarro(Carro carro) {
        if (carro == null) {
            throw new IllegalArgumentException("Use adicionarVazio() para acrescentar o Vazio.");
        }
        adicionar(carro);
    }

    /**
     * Acrescenta o Vazio no fim da lista.
     *
     * @throws IllegalStateException se a lista já tiver um Vazio
     */
    public void adicionarVazio() {
        if (encontrarVazio() != null) {
            throw new IllegalStateException("A lista já tem um Vazio.");
        }
        adicionar(null);
    }

    private void adicionar(Carro carro) {
        No novo = new No(carro);
        if (inicio == null) {
            inicio = novo;
        } else {
            fim.setNext(novo);
        }
        fim = novo;
        fim.setNext(inicio);
        tamanho++;
    }

    /**
     * O carro da frente ocupa o lugar do Vazio e o Vazio avança uma casa.
     *
     * @throws IllegalStateException se a lista não tiver Vazio
     */
    public void moverVazio() {
        No vazio = encontrarVazio();
        if (vazio == null) {
            throw new IllegalStateException("A lista não tem Vazio.");
        }
        No proximo = vazio.getNext();
        vazio.setConteudo(proximo.getConteudo());
        proximo.setConteudo(null);
    }

    /** @return true se alguma vaga for o Vazio */
    public boolean temVazio() {
        return encontrarVazio() != null;
    }

    /**
     * @return a posição do Vazio (0 é o primeiro nó)
     * @throws IllegalStateException se a lista não tiver Vazio
     */
    public int posicaoDoVazio() {
        No atual = inicio;
        int posicao = 0;
        if (atual != null) {
            do {
                if (atual.isVazio()) {
                    return posicao;
                }
                atual = atual.getNext();
                posicao++;
            } while (atual != inicio);
        }
        throw new IllegalStateException("A lista não tem Vazio.");
    }

    /**
     * @param posicao a posição (0 é o primeiro nó)
     * @return o carro dessa posição, ou null se for o Vazio
     * @throws IndexOutOfBoundsException se a posição não existir
     */
    public Carro obter(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + posicao);
        }
        No atual = inicio;
        for (int i = 0; i < posicao; i++) {
            atual = atual.getNext();
        }
        return atual.getConteudo();
    }

    /** @return o número de vagas da lista */
    public int tamanho() {
        return tamanho;
    }

    private No encontrarVazio() {
        if (inicio == null) {
            return null;
        }
        No atual = inicio;
        do {
            if (atual.isVazio()) {
                return atual;
            }
            atual = atual.getNext();
        } while (atual != inicio);
        return null;
    }
}