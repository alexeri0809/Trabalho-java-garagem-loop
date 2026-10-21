# Garagem Loop

Garagem circular em Java com **5 vagas fixas**: 4 carros e 1 **Vazio**. Em cada passo, o carro da frente ocupa essa vaga e o Vazio avança uma casa (sentido horário). Cada carro é mostrado pela **matrícula**, por exemplo `[AA-12-BB]`.

## Correr

Na pasta `demo`:

```bash
javac -encoding UTF-8 -d out src/main/java/com/example/*.java
java -cp out com.example.Main
```

Testes (com Maven):

```bash
mvn test
```

## O que o programa faz

1. Monta a garagem: carro 1, Vazio, carro 4, carro 3, carro 2.
2. Entra num **ciclo infinito**: o Vazio dá voltas sem parar.
3. Termina apenas com **Ctrl+C**.

## Classes

- `Main` — ciclo infinito que mostra o estado a cada passo.
- `Garagem` — as 5 vagas; só conhece a `ListaCircular`, nunca os nós.
- `ListaCircular` — lista ligada circular de vagas.
- `No` — um nó da lista (guarda um `Carro` ou `null` para o Vazio).
- `Carro` — um veículo com id e matrícula.
- `Matricula` — a matrícula (formato `AA-12-BB`).