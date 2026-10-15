package com.example;

/**
 * Garagem de rotação: vagas em círculo, com carros e um único Vazio.
 * A garagem não lida com nós; pede tudo à {@link ListaCircular}.
 */
public class Garagem {

    private static final String TEXTO_VAZIO = "Vazio";

    private final ListaCircular vagas = new ListaCircular();
    private final int posicaoInicialDoVazio;

    /**
     * Cria a garagem com a ordem: Carro1, Vazio, Carro(n-1), ..., Carro2.
     *
     * @param tamanho o número total de vagas (pelo menos 2)
     * @throws IllegalArgumentException se o tamanho for menor que 2
     */
    public Garagem(int tamanho) {
        if (tamanho < 2) {
            throw new IllegalArgumentException("A garagem precisa de pelo menos 2 vagas.");
        }
        vagas.adicionarCarro(new Carro(1));
        vagas.adicionarVazio();
        for (int id = tamanho - 1; id >= 2; id--) {
            vagas.adicionarCarro(new Carro(id));
        }
        posicaoInicialDoVazio = vagas.posicaoDoVazio();
    }

    /** Move o Vazio uma casa no sentido horário. */
    public void moverVazio() {
        vagas.moverVazio();
    }

    /** @return true se o Vazio estiver na posição onde começou */
    public boolean voltouAoInicio() {
        return vagas.posicaoDoVazio() == posicaoInicialDoVazio;
    }

    /**
     * Descreve o estado da garagem numa linha.
     *
     * @return um texto como "[Carro1] [Vazio] [Carro11] ..."
     */
    public String estado() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vagas.tamanho(); i++) {
            Carro carro = vagas.obter(i);
            if (i > 0) {
                sb.append(' ');
            }
            sb.append('[').append(carro == null ? TEXTO_VAZIO : carro.toString()).append(']');
        }
        return sb.toString();
    }
}