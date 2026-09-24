# Exercício Complementar — Módulo 10 (Tópicos em Coleções)

Aplicação única em Java puro (sem dependências externas) que integra os
4 tópicos finais do material de Coleções.

## Estrutura do projeto

```
src/main/java/br/com/ecommerce/
├── modelo/
│   └── Produto.java                # Comparable<Produto> por preço (uso no TreeSet)
├── colecoes/
│   ├── CatalogoPrecos.java         # SortedSet: subSet / headSet / tailSet
│   ├── FilaDePedidos.java          # Queue<String> com LinkedList: offer / peek / poll
│   ├── UtilitariosCollections.java # Collections: shuffle / reverse / min / synchronizedList
│   └── GerenciadorSessoes.java     # Hashtable + Enumeration (hasMoreElements/nextElement)
└── app/
    └── ExercicioComplementarModulo10.java  # main() que demonstra as 4 partes em sequência
```

## Pré-requisitos

- JDK 17+
- Maven 3.8+ (opcional — dá pra compilar só com `javac`, sem dependências externas)

## Como executar

Com Maven:

```bash
mvn clean package
java -jar target/colecoes-modulo10.jar
```

Sem Maven (compilação direta):

```bash
mkdir out
javac -d out $(find src -name "*.java")
java -Dfile.encoding=UTF-8 -cp out br.com.ecommerce.app.ExercicioComplementarModulo10
```

> Nota: use `-Dfile.encoding=UTF-8` se os acentos aparecerem incorretos no
> terminal (depende apenas da configuração de locale do seu console, não do código).

## O que cada parte demonstra

| Parte | Conceito                                               | Classe(s) principal(is)      |
|-------|----------------------------------------------------------|-------------------------------|
| 1     | Subconjuntos de SortedSet (`subSet`, `headSet`, `tailSet`) | `CatalogoPrecos` (TreeSet ordenado por preço) |
| 2     | Contrato formal de `Queue` (`offer`, `peek`, `poll`)       | `FilaDePedidos` (LinkedList)   |
| 3     | Utilitários de `Collections` (`shuffle`, `reverse`, `min`, `synchronizedList`) | `UtilitariosCollections`       |
| 4     | Estrutura legada `Hashtable` percorrida via `Enumeration`  | `GerenciadorSessoes`           |

### Detalhe de implementação importante

`Produto` ordena naturalmente por **preço** (não por nome, como no exercício
principal do Módulo 10) — é essa ordenação que faz sentido para as consultas
de faixa de preço (`subSet`/`headSet`/`tailSet`) pedidas neste exercício
complementar. Para evitar que dois produtos com preços iguais sejam tratados
como duplicados pelo `TreeSet` (que usa `compareTo()`, e não `equals()`, para
decidir igualdade), o desempate é feito pelo código do produto.

## Referência

Material de apoio: Módulo 10 — Tópicos em Coleções (Prof. Alessandro Cerqueira).
