# Garagem Loop

Simulação em Java de uma **garagem circular com 5 vagas**: 4 carros e 1 vaga **Vazio**.
O Vazio anda à volta da garagem sem parar e, **no fim de cada volta, um carro sai e entra
um carro novo com matrícula nova**. O programa só termina com **Ctrl+C**.

Cada carro é mostrado pela matrícula, por exemplo `[AA-12-BB]`.

---

## 1. Como correr

Na pasta `demo`:

```bash
javac -encoding UTF-8 -d out src/main/java/com/example/*.java
java -cp out com.example.Main
```

Testes (com Maven):

```bash
mvn test
```

---

## 2. O que o programa faz

1. Monta a garagem com 5 vagas: `carro 1, Vazio, carro 4, carro 3, carro 2`.
2. A cada 0,7 segundos dá **um passo**: o carro que está à frente do Vazio ocupa a vaga do Vazio,
   e o Vazio avança uma casa (sentido horário).
3. Quando o Vazio volta à posição onde começou, a **volta está completa**. Nesse momento:
   - o carro **mais antigo** sai;
   - entra um **carro novo** com **matrícula nova**, na vaga do que saiu;
   - o contador de voltas mostra as duas matrículas.
4. Repete para sempre.

### Exemplo de saída

```
[DH-17-MP] [Vazio] [NH-68-CL] [KA-51-NV] [GR-34-BF]       <- estado inicial
[DH-17-MP] [NH-68-CL] [Vazio] [KA-51-NV] [GR-34-BF]       <- o Vazio avançou 1 casa
[DH-17-MP] [NH-68-CL] [KA-51-NV] [Vazio] [GR-34-BF]
[DH-17-MP] [NH-68-CL] [KA-51-NV] [GR-34-BF] [Vazio]
[Vazio] [NH-68-CL] [KA-51-NV] [GR-34-BF] [DH-17-MP]       <- o Vazio voltou ao início
=== Volta 1 completa === Saiu: DH-17-MP | Entrou: SR-85-PC
[NH-68-CL] [Vazio] [KA-51-NV] [GR-34-BF] [SR-85-PC]       <- o DH-17-MP saiu, o SR-85-PC entrou
```

Há **sempre 5 vagas, 4 carros e 1 Vazio**: o carro novo fica logo na vaga do que saiu.

---

## 3. As classes

```
Main  ──usa──>  Garagem  ──usa──>  ListaCircular  ──tem──>  No  ──guarda──>  Carro  ──tem──>  Matricula
```

| Classe | O que faz |
|---|---|
| `Main` | Ponto de entrada. Tem o ciclo infinito, espera 0,7 s entre passos e imprime o estado e o contador de voltas. |
| `Garagem` | Representa a garagem. Sabe que tem 5 vagas, dá os passos da rotação, deteta o fim da volta e decide quem sai e quem entra. **Só conhece a `ListaCircular`, nunca os nós.** |
| `ListaCircular` | Lista ligada circular (o último nó aponta para o primeiro). Sabe mover o Vazio e substituir o carro mais antigo. |
| `No` | Um nó da lista = uma vaga. Guarda um `Carro` (ou `null` se for o Vazio) e a ligação ao próximo nó. |
| `Carro` | Um veículo, com um `id` (número de ordem de chegada) e uma `Matricula`. |
| `Matricula` | O texto da matrícula (formato `AA-12-BB`). Sabe gerar uma matrícula a partir do `id`. |

---

## 4. As linhas que fazem o trabalho

### 4.1 O loop infinito — `Main.java`, linhas 22 a 31

```java
while (true) {                                   // linha 22: nunca acaba (só com Ctrl+C)
    Thread.sleep(PAUSA_MS);                      // linha 23: espera 700 ms
    Garagem.Passo passo = garagem.avancar();     // linha 24: dá um passo
    if (passo.voltaCompleta) {                   // linha 25: acabou uma volta?
        voltas++;                                //   conta a volta
        System.out.println("=== Volta " + voltas + " completa === Saiu: "
                + passo.matriculaQueSaiu + " | Entrou: " + passo.matriculaQueEntrou);
    }
    System.out.println(passo.estado);            // linha 30: mostra as 5 vagas
}
```

### 4.2 Montar a garagem — `Garagem.java`, linhas 24 a 32

```java
vagas.adicionarCarro(new Carro(1));              // carro 1
vagas.adicionarVazio();                          // o Vazio
for (int id = VAGAS - 1; id >= 2; id--) {        // carros 4, 3, 2
    vagas.adicionarCarro(new Carro(id));
}
posicaoInicialDoVazio = vagas.posicaoDoVazio();  // linha 30: guarda onde a volta começa
proximoId = VAGAS;                               // linha 31: o próximo carro novo será o nº 5
```

A **posição inicial do Vazio** é o que permite saber quando uma volta acaba.

### 4.3 Um passo da rotação — `Garagem.java`, linhas 40 a 52

```java
public Passo avancar() {
    vagas.moverVazio();                                              // linha 41: o Vazio avança
    boolean volta = vagas.posicaoDoVazio() == posicaoInicialDoVazio; // linha 42: voltou ao início?
    Matricula saiu = null;
    Matricula entrou = null;
    if (volta) {                                                     // linha 45: fim da volta
        Carro novo = new Carro(proximoId++);                         // linha 46: carro novo, matrícula nova
        Carro antigo = vagas.substituirMaisAntigo(novo);             // linha 47: o mais antigo sai
        saiu = antigo.getMatricula();
        entrou = novo.getMatricula();
    }
    return new Passo(estado(), volta, saiu, entrou);                 // linha 51
}
```

### 4.4 Mover o Vazio — `ListaCircular.java`, linhas 55 a 63

```java
No vazio = encontrarVazio();                  // procura o nó que está vazio
No proximo = vazio.getNext();                 // o nó seguinte
vazio.setConteudo(proximo.getConteudo());     // o carro seguinte passa para a vaga do Vazio
proximo.setConteudo(null);                    // a vaga onde o carro estava passa a ser o Vazio
```

Nenhum nó muda de sítio: **só o conteúdo** (o carro) é que passa de um nó para o vizinho.
Por isso o Vazio parece andar e os carros parecem recuar uma casa.

### 4.5 Sai o mais antigo, entra o novo — `ListaCircular.java`, linhas 73 a 95

```java
do {                                                              // linhas 82 a 88
    Carro carro = atual.getConteudo();
    if (carro != null && (maisAntigo == null
            || carro.getId() < maisAntigo.getConteudo().getId())) {
        maisAntigo = atual;                                       // guarda o carro de menor id
    }
    atual = atual.getNext();
} while (atual != inicio);                                        // dá uma volta à lista

Carro saiu = maisAntigo.getConteudo();                            // linha 92
maisAntigo.setConteudo(novo);                                     // linha 93: o novo fica na vaga dele
return saiu;
```

O carro **mais antigo** é o de **menor `id`**, porque o `id` aumenta a cada carro que chega.

### 4.6 Gerar a matrícula — `Matricula.java`, linha 32

`Matricula.gerar(id)` calcula as letras e os números a partir do `id` com contas simples
(multiplicações e resto da divisão). O mesmo `id` dá sempre a mesma matrícula e, nas
primeiras 2100 voltas, ids diferentes dão matrículas diferentes, por isso **não se repete
nenhuma matrícula** durante uma demonstração.

---

## 5. Ideias importantes para a apresentação

- **Lista ligada circular:** o último nó aponta para o primeiro, por isso o Vazio pode andar
  para sempre sem nunca chegar a um "fim".
- **O Vazio é um `null`:** uma vaga com conteúdo `null` é o Vazio. Há sempre exatamente um.
- **Encapsulamento:** a `Garagem` não vê os nós, só pede coisas à `ListaCircular`
  (`moverVazio`, `substituirMaisAntigo`, `obter`).
- **Como se deteta uma volta:** guardamos a posição inicial do Vazio e comparamos a posição
  atual com ela a cada passo.
- **Matrículas novas:** cada carro novo recebe o `id` seguinte (5, 6, 7, ...), logo uma
  matrícula diferente.

---

## 6. Testes

Os testes (JUnit 5, em `src/test`) verificam, entre outras coisas, que:

- a garagem tem sempre 5 vagas, 4 carros e 1 Vazio;
- o estado inicial é `carro 1, Vazio, carro 4, carro 3, carro 2`;
- uma volta completa acontece ao 5.º passo;
- no fim da volta sai o carro mais antigo e entra um novo;
- nenhuma matrícula nova se repete ao longo de 10 voltas;
- não é possível ter dois Vazios, nem mover o Vazio numa lista sem Vazio.

> As linhas indicadas na secção 4 são as do código atual. Se mudares o código, confirma-as.
