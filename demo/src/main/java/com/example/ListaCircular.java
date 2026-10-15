package com.example;

/**
 * Lista ligada circular de vagas. O último nó aponta de volta para o primeiro.
 * Esconde os nós de quem a usa: o resto do programa só trabalha com carros e posições.
 * Uma vaga com conteúdo null representa o Vazio.
 */
public class ListaCircular {

    private Nó inicio; // primeiro nó
    private Nó fim;    // último nó (aponta para o início)
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

    /** Cria um nó novo, liga-o ao fim e fecha sempre o círculo. */
    private void adicionar(Carro carro) {
        Nó novo = new Nó(carro);
        if (inicio == null) {
            inicio = novo;
        } else {
            fim.setNext(novo);
        }
        fim = novo;
        fim.setNext(inicio); // o último aponta de volta para o primeiro
        tamanho++;
    }

    /**
     * Move o Vazio uma casa no sentido normal (horário): o carro da frente
     * ocupa o lugar do Vazio e o Vazio avança.
     *
     * @throws IllegalStateException se a lista não tiver Vazio
     */
    public void moverVazio() {
        Nó vazio = encontrarVazio();
        if (vazio == null) {
            throw new IllegalStateException("A lista não tem Vazio.");
        }
        Nó proximo = vazio.getNext();
        vazio.setConteudo(proximo.getConteudo()); // o carro passa para a vaga do Vazio
        proximo.setConteudo(null);                // o Vazio avança uma casa
    }

    /**
     * Indica em que posição está o Vazio.
     *
     * @return a posição do Vazio (0 é o primeiro nó)
     * @throws IllegalStateException se a lista não tiver Vazio
     */
    public int posicaoDoVazio() {
        Nó atual = inicio;
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
     * Devolve o conteúdo de uma posição.
     *
     * @param posicao a posição (0 é o primeiro nó)
     * @return o carro dessa posição, ou null se for o Vazio
     * @throws IndexOutOfBoundsException se a posição não existir
     */
    public Carro obter(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + posicao);
        }
        Nó atual = inicio;
        for (int i = 0; i < posicao; i++) {
            atual = atual.getNext();
        }
        return atual.getConteudo();
    }

    /** @return o número de vagas da lista */
    public int tamanho() {
        return tamanho;
    }

    /** Percorre o círculo até encontrar o nó vazio; devolve null se não houver. */
    private Nó encontrarVazio() {
        if (inicio == null) {
            return null;
        }
        Nó atual = inicio;
        do {
            if (atual.isVazio()) {
                return atual;
            }
            atual = atual.getNext();
        } while (atual != inicio);
        return null;
    }
}