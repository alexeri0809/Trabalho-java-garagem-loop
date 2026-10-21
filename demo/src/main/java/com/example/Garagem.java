package com.example;

/**
 * Garagem de rotação com 5 vagas em círculo: 4 carros e 1 Vazio.
 * Em cada passo o carro da frente ocupa o Vazio e o Vazio avança uma casa.
 * No fim de cada volta, o carro mais antigo sai e entra um carro novo com matrícula nova.
 * A Garagem só conhece a ListaCircular, nunca os nós.
 */
public class Garagem {

    /** Número fixo de vagas: 4 carros e 1 Vazio. */
    public static final int VAGAS = 5;

    private static final String TEXTO_VAZIO = "Vazio";

    private final ListaCircular vagas = new ListaCircular();
    private final int posicaoInicialDoVazio;
    private int proximoId;

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
        proximoId = VAGAS;
    }

    /**
     * Dá um passo da rotação: o Vazio avança uma casa. Se isso completar uma volta,
     * o carro mais antigo sai e entra um carro novo com matrícula nova.
     *
     * @return o estado depois do passo e, no fim de uma volta, as matrículas que saíram e entraram
     */
    public Passo avancar() {
        vagas.moverVazio();
        boolean volta = vagas.posicaoDoVazio() == posicaoInicialDoVazio;
        Matricula saiu = null;
        Matricula entrou = null;
        if (volta) {
            Carro novo = new Carro(proximoId++);
            Carro antigo = vagas.substituirMaisAntigo(novo);
            saiu = antigo.getMatricula();
            entrou = novo.getMatricula();
        }
        return new Passo(estado(), volta, saiu, entrou);
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
        /** Matrícula do carro que saiu no fim da volta, ou null se a volta não acabou. */
        public final Matricula matriculaQueSaiu;
        /** Matrícula do carro que entrou no fim da volta, ou null se a volta não acabou. */
        public final Matricula matriculaQueEntrou;

        public Passo(String estado, boolean voltaCompleta,
                     Matricula matriculaQueSaiu, Matricula matriculaQueEntrou) {
            this.estado = estado;
            this.voltaCompleta = voltaCompleta;
            this.matriculaQueSaiu = matriculaQueSaiu;
            this.matriculaQueEntrou = matriculaQueEntrou;
        }
    }
}