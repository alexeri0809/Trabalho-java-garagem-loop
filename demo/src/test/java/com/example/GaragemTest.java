package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GaragemTest {

    @Test
    void estadoInicialTemCarro1VazioEDepoisOsRestantes() {
        Garagem garagem = new Garagem(12);
        assertEquals(slot(1) + " [Vazio] " + slot(11) + " " + slot(10) + " " + slot(9) + " "
                + slot(8) + " " + slot(7) + " " + slot(6) + " " + slot(5) + " " + slot(4) + " "
                + slot(3) + " " + slot(2), garagem.estado());
    }

    @Test
    void moverVazioAvancaUmaCasa() {
        Garagem garagem = new Garagem(12);
        garagem.moverVazio();
        assertTrue(garagem.estado().startsWith(slot(1) + " " + slot(11) + " [Vazio]"));
    }

    @Test
    void vazioVoltaAoInicioDepoisDeUmaVoltaCompleta() {
        Garagem garagem = new Garagem(12);
        for (int i = 1; i < 12; i++) {
            garagem.moverVazio();
            assertFalse(garagem.voltouAoInicio());
        }
        garagem.moverVazio();
        assertTrue(garagem.voltouAoInicio());
    }

    @Test
    void nenhumaMatriculaSePerdeDuranteAsRotacoes() {
        Garagem garagem = new Garagem(12);
        for (int i = 0; i < 25; i++) {
            garagem.moverVazio();
        }
        for (int id = 1; id <= 11; id++) {
            assertTrue(garagem.estado().contains(slot(id)), "Falta " + slot(id));
        }
    }

    @Test
    void haSempreUmUnicoVazio() {
        Garagem garagem = new Garagem(12);
        for (int i = 0; i < 30; i++) {
            garagem.moverVazio();
            assertEquals(1, contarVazios(garagem.estado()));
        }
    }

    @Test
    void entrarOcupaOVazioComAMatriculaEscolhida() {
        Garagem garagem = new Garagem(4);
        garagem.entrar(new Matricula("aa-12-bb"));
        assertFalse(garagem.estado().contains("Vazio"));
        assertTrue(garagem.estado().contains("[AA-12-BB]"));
        assertThrows(IllegalStateException.class, () -> garagem.entrar(new Matricula("BB-99-CC")));
    }

    @Test
    void sairSoQuandoEstaCheiaECriaUmVazio() {
        Garagem garagem = new Garagem(4);
        assertThrows(IllegalStateException.class, () -> garagem.sair(Matricula.gerar(1).getValor()));
        garagem.entrar(new Matricula("AA-12-BB"));
        garagem.sair("AA-12-BB");
        assertEquals(1, contarVazios(garagem.estado()));
        assertFalse(garagem.estado().contains("[AA-12-BB]"));
    }

    @Test
    void tamanhoInvalidoLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> new Garagem(1));
    }

    private static String slot(int id) {
        return "[" + Matricula.gerar(id).getValor() + "]";
    }

    private static int contarVazios(String estado) {
        return estado.split("Vazio", -1).length - 1;
    }
}
