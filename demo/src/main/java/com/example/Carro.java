package com.example;

/**
 * Um veículo da garagem, identificado por um número e por uma matrícula.
 * O texto do carro é a matrícula, por exemplo AA-12-BB.
 */
public class Carro {

    private final int id;
    private final Matricula matricula;

    /**
     * Cria um carro com matrícula gerada a partir do id.
     *
     * @param id o identificador do carro
     */
    public Carro(int id) {
        this(id, Matricula.gerar(id));
    }

    /**
     * Cria um carro com a matrícula escolhida.
     *
     * @param id o identificador do carro
     * @param matricula a matrícula, por exemplo AA-12-BB
     * @throws IllegalArgumentException se a matrícula for null
     */
    public Carro(int id, Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException("O carro precisa de matrícula.");
        }
        this.id = id;
        this.matricula = matricula;
    }

    /** @return o identificador do carro */
    public int getId() {
        return id;
    }

    /** @return a matrícula do carro */
    public Matricula getMatricula() {
        return matricula;
    }

    /** @return a matrícula, por exemplo "AA-12-BB" */
    @Override
    public String toString() {
        return matricula.toString();
    }
}
