package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CarroTest {

    @Test
    void toStringMostraAMatricula() {
        assertEquals("AA-12-BB", new Carro(7, new Matricula("aa-12-bb")).toString());
    }

    @Test
    void construtorSimplesGeraMatricula() {
        Carro carro = new Carro(7);
        assertEquals(7, carro.getId());
        assertEquals(Matricula.gerar(7).getValor(), carro.toString());
    }
}
