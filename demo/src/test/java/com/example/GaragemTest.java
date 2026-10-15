package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GaragemTest {

    @Test
    void garagemComDuasVagasFunciona() {
        Garagem garagem = new Garagem(2);
        assertEquals("[Carro1] [Vazio]", garagem.estado());
        garagem.moverVazio();
        assertEquals("[Vazio] [Carro1]", garagem.estado());
    }

    @Test
    void nenhumCarroSePerdeDuranteAsRotacoes() {
        Garagem garagem = new Garagem(12);
        for (int i = 0; i < 25; i++) {
            garagem.moverVazio();
        }
        for (int id = 1; id <= 11; id++) {
            assertTrue(garagem.estado().contains("[Carro" + id + "]"),
                    "Falta o Carro" + id);
        }
    }

    @Test
    void estadoInicialEstaCorreto() {
        Garagem garagem = new Garagem(12);
        assertEquals("[Carro1] [Vazio] [Carro11] [Carro10] [Carro9] [Carro8] "
                + "[Carro7] [Carro6] [Carro5] [Carro4] [Carro3] [Carro2]", garagem.estado());
    }

    @Test
    void moverVazioAvancaUmaCasa() {
        Garagem garagem = new Garagem(12);
        garagem.moverVazio();
        assertEquals("[Carro1] [Carro11] [Vazio] [Carro10] [Carro9] [Carro8] "
                + "[Carro7] [Carro6] [Carro5] [Carro4] [Carro3] [Carro2]", garagem.estado());
    }

    @Test
    void vazioVoltaAoInicioDepoisDeUmaVoltaCompleta() {
        Garagem garagem = new Garagem(12);
        for (int i = 1; i < 12; i++) {
            garagem.moverVazio();
            assertFalse(garagem.voltouAoInicio());
        }
        garagem.moverVazio(); // 12.º movimento
        assertTrue(garagem.voltouAoInicio());
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
    void tamanhoInvalidoLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> new Garagem(1));
    }

    private int contarVazios(String estado) {
        return estado.split("Vazio", -1).length - 1;
    }
}