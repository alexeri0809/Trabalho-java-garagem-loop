package com.example;

import java.util.Objects;

/**
 * Matrícula de um carro. Guarda o texto em maiúsculas, sem espaços nas pontas.
 */
public class Matricula {

    private static final String LETRAS = "ABCDEFGHJKLMNPRSTUVXZ";

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

    /**
     * Gera uma matrícula estável a partir do identificador do carro.
     * O formato é o do exemplo do trabalho: AA-12-BB.
     *
     * @param id identificador positivo do carro
     * @return a matrícula correspondente
     */
    public static Matricula gerar(int id) {
        int n = LETRAS.length();
        char a = LETRAS.charAt(Math.floorMod(id * 3, n));
        char b = LETRAS.charAt(Math.floorMod(id * 7, n));
        String numero = String.format("%02d", Math.floorMod(id * 17, 100));
        char c = LETRAS.charAt(Math.floorMod(id * 11, n));
        char d = LETRAS.charAt(Math.floorMod(id * 13, n));
        return new Matricula("" + a + b + "-" + numero + "-" + c + d);
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
