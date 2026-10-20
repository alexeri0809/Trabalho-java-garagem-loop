package com.example;

import java.util.Objects;

/**
 * Matrícula de um carro. Guarda o texto em maiúsculas, sem espaços nas pontas.
 */
public class Matricula {

    private final String valor;

    /**
     * @param valor o texto da matrícula, por exemplo "AA-12-BB"
     * @throws IllegalArgumentException se o texto for null ou vazio
     */
    public Matricula(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("A matrícula não pode estar vazia.");
        }
        this.valor = valor.trim().toUpperCase();
    }

    /** @return o texto da matrícula */
    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (!(outro instanceof Matricula)) return false;
        return valor.equals(((Matricula) outro).valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
