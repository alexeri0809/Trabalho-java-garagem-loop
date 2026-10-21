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
    void passosNoMeioDaVoltaNaoTrocamCarros() {
        Garagem garagem = new Garagem();
        for (int i = 1; i < 5; i++) {
            Garagem.Passo passo = garagem.avancar();
            assertNull(passo.matriculaQueSaiu);
            assertNull(passo.matriculaQueEntrou);
        }
    }

    @Test
    void noFimDaPrimeiraVoltaSaiOCarroMaisAntigoEEntraUmNovo() {
        Garagem garagem = new Garagem();
        Garagem.Passo passo = null;
        for (int i = 0; i < 5; i++) {
            passo = garagem.avancar();
        }
        assertTrue(passo.voltaCompleta);
        assertEquals(Matricula.gerar(1), passo.matriculaQueSaiu);
        assertEquals(Matricula.gerar(5), passo.matriculaQueEntrou);
        assertFalse(passo.estado.contains(slot(1)));
        assertTrue(passo.estado.contains(slot(5)));
        assertEquals(1, contarVazios(passo.estado));
    }

    @Test
    void aCadaVoltaSaiUmCarroEEntraUmComMatriculaNova() {
        Garagem garagem = new Garagem();
        java.util.Set<String> jaEntraram = new java.util.HashSet<>();
        for (int id = 1; id <= 4; id++) {
            jaEntraram.add(Matricula.gerar(id).getValor());
        }
        int voltas = 0;
        for (int i = 1; i <= 50; i++) {
            Garagem.Passo passo = garagem.avancar();
            if (passo.voltaCompleta) {
                voltas++;
                assertTrue(jaEntraram.add(passo.matriculaQueEntrou.getValor()),
                        "Matrícula repetida: " + passo.matriculaQueEntrou);
                assertNotEquals(passo.matriculaQueSaiu, passo.matriculaQueEntrou);
            }
        }
        assertEquals(10, voltas);
    }

    @Test
    void depoisDeDezVoltasTemOsQuatroCarrosMaisRecentes() {
        Garagem garagem = new Garagem();
        for (int i = 0; i < 50; i++) {
            garagem.avancar();
        }
        for (int id = 11; id <= 14; id++) {
            assertTrue(garagem.estado().contains(slot(id)), "Falta " + slot(id));
        }
        assertFalse(garagem.estado().contains(slot(1)));
        assertFalse(garagem.estado().contains(slot(10)));
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