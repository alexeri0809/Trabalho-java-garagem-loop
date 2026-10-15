package com.example;

/**
 * Um veículo da garagem, identificado por um número (ID).
 */
public class Carro {

    private final int id;

    /**
     * Cria um carro.
     *
     * @param id o identificador do carro
     */
    public Carro(int id) {
        this.id = id;
    }

    /** @return o identificador do carro */
    public int getId() {
        return id;
    }

    /** @return o nome do carro, por exemplo "Carro3" */
    @Override
    public String toString() {
        return "Carro" + id;
    }
}