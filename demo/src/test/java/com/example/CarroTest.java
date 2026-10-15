package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CarroTest {

    @Test
    void toStringMostraOId() {
        assertEquals("Carro7", new Carro(7).toString());
    }

    @Test
    void getIdDevolveOId() {
        assertEquals(7, new Carro(7).getId());
    }
}