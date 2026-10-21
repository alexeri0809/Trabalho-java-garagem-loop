package com.example;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Pede o número de vagas e deixa o Vazio a dar voltas sem parar,
 * até o professor escrever "parar" ou terminar o programa.
 * Comandos durante a rotação: entrar AA-12-BB | sair AA-12-BB | parar
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("=== Garagem em loop ===");
        int tamanho = lerTamanho(in);

        Garagem garagem = new Garagem(tamanho);
        System.out.println("Estado inicial:");
        System.out.println(garagem.estado());
        System.out.println();
        System.out.println("O Vazio roda sem parar. O professor para com: parar");
        System.out.println("Também podes terminar o programa com Ctrl+C.");
        System.out.println("Comandos:");
        System.out.println("  entrar AA-12-BB   escolhe o carro que entra na vaga vazia");
        System.out.println("  sair AA-12-BB     escolhe o carro que sai (só com a garagem cheia)");
        System.out.println("  parar             acaba o programa");
        System.out.println();

        AtomicBoolean correr = new AtomicBoolean(true);
        Thread voltas = new Thread(() -> rodar(garagem, correr), "voltas");
        voltas.setDaemon(true);
        voltas.start();

        while (correr.get() && in.hasNextLine()) {
            String linha = in.nextLine().trim();
            if (linha.isEmpty()) {
                continue;
            }
            if (linha.equalsIgnoreCase("parar")) {
                correr.set(false);
                break;
            }
            try {
                executar(garagem, linha);
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }

        correr.set(false);
        voltas.join(1500);
        System.out.println("Programa parado.");
        System.out.println(garagem.estado());
    }

    private static int lerTamanho(Scanner in) {
        while (true) {
            System.out.print("Número de vagas (mínimo 2): ");
            if (!in.hasNextInt()) {
                System.out.println("Escreve um número inteiro.");
                if (!in.hasNextLine()) {
                    return 12;
                }
                in.nextLine();
                continue;
            }
            int tamanho = in.nextInt();
            in.nextLine();
            if (tamanho >= 2) {
                return tamanho;
            }
            System.out.println("A garagem precisa de pelo menos 2 vagas.");
        }
    }

    private static void executar(Garagem garagem, String linha) {
        String lower = linha.toLowerCase();
        if (lower.startsWith("entrar ")) {
            String placa = linha.substring(7).trim();
            garagem.entrar(new Matricula(placa));
            System.out.println("Entrou " + new Matricula(placa).getValor());
            System.out.println(garagem.estado());
            return;
        }
        if (lower.startsWith("sair ")) {
            String placa = linha.substring(5).trim();
            garagem.sair(placa);
            System.out.println("Saiu " + new Matricula(placa).getValor());
            System.out.println(garagem.estado());
            return;
        }
        System.out.println("Comando desconhecido. Usa: entrar AA-12-BB | sair AA-12-BB | parar");
    }

    private static void rodar(Garagem garagem, AtomicBoolean correr) {
        int voltas = 0;
        while (correr.get()) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException ex) {
                return;
            }
            if (!correr.get()) {
                return;
            }
            Garagem.Passo passo = garagem.avancar();
            if (!passo.moveu) {
                continue;
            }
            if (passo.voltaCompleta) {
                voltas++;
                System.out.println("=== Volta " + voltas + " completa ===");
            }
            System.out.println(passo.estado);
        }
    }
}
