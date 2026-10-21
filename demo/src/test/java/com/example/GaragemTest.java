package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GaragemTest {

    @Test
    void temSempreCincoVagas() {
        assertEquals(5, Garagem.VAGAS);
        assertEquals(5, new Garagem().estado().split(" ").length);
    }

    @Test
    void estadoInicialTemCarro1VazioEDepoisOsRestantes() {
        Garagem garagem = new Garagem();
        assertEquals(slot(1) + " [Vazio] " + slot(4) + " " + slot(3) + " " + slot(2),
                garagem.estado());
    }

    @Test
    void avancarMoveOVazioUmaCasa() {
        Garagem garagem = new Garagem();
        Garagem.Passo passo = garagem.avancar();
        assertEquals(slot(1) + " " + slot(4) + " [Vazio] " + slot(3) + " " + slot(2),
                passo.estado);
        assertFalse(passo.voltaCompleta);
    }

    @Test
    void voltaCompletaAcontecePeloQuintoPasso() {
        Garagem garagem = new Garagem();
        for (int i = 1; i < 5; i++) {
            assertFalse(garagem.avancar().voltaCompleta, "Passo " + i);
        }
        assertTrue(garagem.avancar().voltaCompleta);
    }

    @Test
    void nenhumaMatriculaSePerdeDuranteAsRotacoes() {
        Garagem garagem = new Garagem();
        for (int i = 0; i < 50; i++) {
            garagem.avancar();
        }
        for (int id = 1; id <= 4; id++) {
            assertTrue(garagem.estado().contains(slot(id)), "Falta " + slot(id));
        }
    }

    @Test
    void haSempreUmUnicoVazioEQuatroCarros() {
        Garagem garagem = new Garagem();
        for (int i = 0; i < 50; i++) {
            String estado = garagem.avancar().estado;
            assertEquals(1, contarVazios(estado));
            assertEquals(5, estado.split(" ").length);
        }
    }

    private static String slot(int id) {
        return "[" + Matricula.gerar(id).getValor() + "]";
    }

    private static int contarVazios(String estado) {
        return estado.split("Vazio", -1).length - 1;
    }
}