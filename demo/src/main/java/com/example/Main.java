package com.example;

/**
 * Ponto de entrada: simula voltas completas do Vazio na garagem.
 */
public class Main {

    private static final int TAMANHO = 12; // número de vagas
    private static final int VOLTAS = 2;   // quantas voltas completas simular

        /**
     * Cria a garagem e simula as voltas completas do Vazio.
     *
     * @param args argumentos da linha de comandos (não usados)
     */
    public static void main(String[] args) {
        Garagem garagem = new Garagem(TAMANHO);

        System.out.println("Estado inicial:");
        System.out.println(garagem.estado());
        System.out.println();

        for (int volta = 1; volta <= VOLTAS; volta++) {
            System.out.println("=== Volta " + volta + " ===");
            int movimento = 0;
            do {
                movimento++;
                System.out.println("Movimento " + movimento + " - antes:");
                System.out.println(garagem.estado());

                garagem.moverVazio();

                System.out.println("Movimento " + movimento + " - depois:");
                System.out.println(garagem.estado());
                System.out.println();
            } while (!garagem.voltouAoInicio()); // até o Vazio dar a volta completa
        }
    }

    
}