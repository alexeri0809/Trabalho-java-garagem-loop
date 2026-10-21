package com.example;

/**
 * Garagem de rotação com 5 vagas em círculo: 4 carros e 1 Vazio.
 * Em cada passo o carro da frente ocupa o Vazio e o Vazio avança uma casa.
 * A Garagem só conhece a ListaCircular, nunca os nós.
 */
public class Garagem {

    /** Número fixo de vagas: 4 carros e 1 Vazio. */
    public static final int VAGAS = 5;

    private static final String TEXTO_VAZIO = "Vazio";

    private final ListaCircular vagas = new ListaCircular();
    private final int posicaoInicialDoVazio;

    /**
     * Ordem inicial: carro 1, Vazio, carro 4, carro 3, carro 2.
     * Cada carro recebe uma matrícula no formato AA-12-BB.
     */
    public Garagem() {
        vagas.adicionarCarro(new Carro(1));
        vagas.adicionarVazio();
        for (int id = VAGAS - 1; id >= 2; id--) {
            vagas.adicionarCarro(new Carro(id));
        }
        posicaoInicialDoVazio = vagas.posicaoDoVazio();
    }

    /**
     * Dá um passo da rotação: o Vazio avança uma casa.
     *
     * @return o estado depois do passo e se o Vazio completou uma volta
     */
    public Passo avancar() {
        vagas.moverVazio();
        boolean volta = vagas.posicaoDoVazio() == posicaoInicialDoVazio;
        return new Passo(estado(), volta);
    }

    /**
     * @return um texto como "[DH-17-MP] [Vazio] [AA-12-BB] [CC-34-DD] [EE-56-FF]"
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

    /** O resultado de um passo da rotação. */
    public static final class Passo {
        public final String estado;
        public final boolean voltaCompleta;

        public Passo(String estado, boolean voltaCompleta) {
            this.estado = estado;
            this.voltaCompleta = voltaCompleta;
        }
    }
}