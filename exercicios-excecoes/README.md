# Lista de Exercícios sobre Exceções

Aluno: Jhonatan Carlos Rodrigues Queiroz

## Exercícios

1. **Divisão Segura** — `Exercicio1_DivisaoSegura.java`
   Lê dois inteiros e divide o primeiro pelo segundo, tratando `ArithmeticException` (divisão por zero) e `InputMismatchException` (entrada inválida), com bloco `finally`.

2. **Acesso a Posições de Array** — `Exercicio2_AcessoArray.java`
   Array fixo com 5 cidades; trata `ArrayIndexOutOfBoundsException` para índices inválidos.

3. **Validação de Idade** — `Exercicio3_ValidacaoIdade.java` + `IdadeInvalidaException.java`
   Exceção personalizada (`RuntimeException`) lançada para idade fora do intervalo 0–150.

4. **Conversor Numérico com Propagação (throws)** — `Exercicio4_ConversorNumerico.java`
   Método `converterParaInteiro` propaga `NumberFormatException` via `throws`; tratada no `main`.

5. **Cadastro de Conta Bancária** — `Exercicio5_ContaBancaria.java` + `ContaBancaria.java` + `SaldoInsuficienteException.java`
   Classe `ContaBancaria` com `sacar(double valor)` que lança `SaldoInsuficienteException` quando o valor excede o saldo. Testa um saque válido e um saque inválido.

## Como compilar e executar

Cada exercício pode ser compilado e executado individualmente. Exemplo para o exercício 1:

```bash
javac Exercicio1_DivisaoSegura.java
java Exercicio1_DivisaoSegura
```

Para os exercícios 3 e 5, compile também as classes de exceção/apoio:

```bash
javac Exercicio3_ValidacaoIdade.java IdadeInvalidaException.java
java Exercicio3_ValidacaoIdade

javac Exercicio5_ContaBancaria.java ContaBancaria.java SaldoInsuficienteException.java
java Exercicio5_ContaBancaria
```
