package com.example;

/**
 * Garagem com 5 vagas (4 carros e 1 Vazio). O Vazio roda para sempre;
 * o programa só termina com Ctrl+C.
 */
public class Main {

    private static final long PAUSA_MS = 700;

    public static void main(String[] args) throws InterruptedException {
        Garagem garagem = new Garagem();
        System.out.println("=== Garagem em loop (" + Garagem.VAGAS + " vagas) ===");
        System.out.println("Estado inicial:");
        System.out.println(garagem.estado());
        System.out.println();
        System.out.println("O Vazio roda sem parar. Termina com Ctrl+C.");
        System.out.println();

        int voltas = 0;
        while (true) {
            Thread.sleep(PAUSA_MS);
            Garagem.Passo passo = garagem.avancar();
            if (passo.voltaCompleta) {
                voltas++;
                System.out.println("=== Volta " + voltas + " completa ===");
            }
            System.out.println(passo.estado);
        }
    }
}