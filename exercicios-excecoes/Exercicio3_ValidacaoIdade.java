// Jhonatan Carlos Rodrigues Queiroz

import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercicio3_ValidacaoIdade {

    public static void validarIdade(int idade) {
        if (idade < 0 || idade > 150) {
            throw new IdadeInvalidaException("Idade inválida: " + idade + ". A idade deve estar entre 0 e 150.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Digite a idade da pessoa: ");
            int idade = sc.nextInt();

            validarIdade(idade);

            System.out.println("Idade válida: " + idade);

        } catch (IdadeInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Erro: digite apenas números inteiros!");
        } finally {
            System.out.println("Fim da operação");
        }

        sc.close();
    }
}
