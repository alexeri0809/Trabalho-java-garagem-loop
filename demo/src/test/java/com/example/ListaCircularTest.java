package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ListaCircularTest {

    @Test
    void adicionarCarroNullLancaExcecao() {
        ListaCircular lista = new ListaCircular();
        assertThrows(IllegalArgumentException.class, () -> lista.adicionarCarro(null));
    }

    @Test
    void listaVaziaNaoTemPosicaoDoVazio() {
        assertThrows(IllegalStateException.class, () -> new ListaCircular().posicaoDoVazio());
    }

    @Test
    void moverVazioTrocaComOProximo() {
        Carro c1 = new Carro(1);
        Carro c2 = new Carro(2);
        ListaCircular lista = new ListaCircular();
        lista.adicionarCarro(c1);
        lista.adicionarVazio();
        lista.adicionarCarro(c2);

        lista.moverVazio();

        assertSame(c1, lista.obter(0));
        assertSame(c2, lista.obter(1));
        assertNull(lista.obter(2));
    }

    @Test
    void moverVazioDaVoltaAoInicioDaLista() {
        Carro c1 = new Carro(1);
        Carro c2 = new Carro(2);
        ListaCircular lista = new ListaCircular();
        lista.adicionarCarro(c1);
        lista.adicionarCarro(c2);
        lista.adicionarVazio();

        lista.moverVazio();

        assertEquals(0, lista.posicaoDoVazio());
        assertSame(c1, lista.obter(2));
    }

    @Test
    void naoPermiteDoisVazios() {
        ListaCircular lista = new ListaCircular();
        lista.adicionarVazio();
        assertThrows(IllegalStateException.class, lista::adicionarVazio);
    }

    @Test
    void moverVazioSemVazioLancaExcecao() {
        ListaCircular lista = new ListaCircular();
        lista.adicionarCarro(new Carro(1));
        assertThrows(IllegalStateException.class, lista::moverVazio);
    }
}