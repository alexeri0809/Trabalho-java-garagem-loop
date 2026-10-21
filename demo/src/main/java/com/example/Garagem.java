package com.example;

/**
 * Garagem de rotação: vagas em círculo, com carros (matrícula) e um único Vazio.
 * Quando a garagem está cheia não há Vazio e a rotação espera que um carro saia.
 */
public class Garagem {

    private static final String TEXTO_VAZIO = "Vazio";

    private final ListaCircular vagas = new ListaCircular();
    private int posicaoInicialDoVazio;

    /**
     * Ordem inicial: carro 1, Vazio, carro (n-1), ..., carro 2.
     * Cada carro recebe uma matrícula no formato AA-12-BB.
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
    public synchronized void moverVazio() {
        vagas.moverVazio();
    }

    /**
     * Dá um passo da rotação.
     *
     * @return o resultado do passo; {@code moveu} é false se a garagem estiver cheia
     */
    public synchronized Passo avancar() {
        if (!vagas.temVazio()) {
            return new Passo(estadoInterno(), false, false);
        }
        vagas.moverVazio();
        boolean volta = vagas.posicaoDoVazio() == posicaoInicialDoVazio;
        return new Passo(estadoInterno(), true, volta);
    }

    /** @return true se o Vazio estiver na posição onde a volta começou */
    public synchronized boolean voltouAoInicio() {
        return vagas.temVazio() && vagas.posicaoDoVazio() == posicaoInicialDoVazio;
    }

    /** @return true se existir uma vaga vazia */
    public synchronized boolean temVazio() {
        return vagas.temVazio();
    }

    /**
     * O carro com esta matrícula ocupa a vaga do Vazio.
     *
     * @param matricula a matrícula escolhida
     * @throws IllegalStateException se não houver Vazio ou a matrícula já estiver dentro
     */
    public synchronized void entrar(Matricula matricula) {
        if (vagas.contemMatricula(matricula.getValor())) {
            throw new IllegalStateException("Essa matrícula já está na garagem.");
        }
        if (!vagas.temVazio()) {
            throw new IllegalStateException("Não há vaga vazia.");
        }
        vagas.colocarNoVazio(new Carro(vagas.maiorId() + 1, matricula));
    }

    /**
     * O carro sai e a vaga dele passa a ser o Vazio.
     * Só é permitido quando a garagem está cheia, para continuar a haver um único Vazio.
     *
     * @param placa o texto da matrícula
     * @throws IllegalStateException se já existir Vazio ou a matrícula não estiver dentro
     */
    public synchronized void sair(String placa) {
        if (vagas.temVazio()) {
            throw new IllegalStateException("Já existe um Vazio. Escolhe um carro para entrar.");
        }
        Matricula matricula = new Matricula(placa);
        if (!vagas.retirarPorMatricula(matricula.getValor())) {
            throw new IllegalStateException("Não há carro com essa matrícula.");
        }
        posicaoInicialDoVazio = vagas.posicaoDoVazio();
    }

    /**
     * @return um texto como "[DH-17-MP] [Vazio] [AA-12-BB]"
     */
    public synchronized String estado() {
        return estadoInterno();
    }

    private String estadoInterno() {
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

    /** Um passo da rotação infinita. */
    public static final class Passo {
        public final String estado;
        public final boolean moveu;
        public final boolean voltaCompleta;

        public Passo(String estado, boolean moveu, boolean voltaCompleta) {
            this.estado = estado;
            this.moveu = moveu;
            this.voltaCompleta = voltaCompleta;
        }
    }
}
