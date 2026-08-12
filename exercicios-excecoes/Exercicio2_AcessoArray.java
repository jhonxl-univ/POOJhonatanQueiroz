// Jhonatan Carlos Rodrigues Queiroz

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 2. Acesso a Posições de Array
 *
 * Cria um array fixo com 5 nomes de cidades e pede ao usuário um índice
 * de 0 a 4 para exibir a cidade correspondente, tratando índices
 * inválidos com ArrayIndexOutOfBoundsException.
 */
public class Exercicio2_AcessoArray {

    public static void main(String[] args) {
        String[] cidades = { "São Paulo", "Rio de Janeiro", "Curitiba", "Salvador", "Manaus" };

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Digite um número de 0 a 4 para escolher uma cidade: ");
            int indice = sc.nextInt();

            System.out.println("Cidade escolhida: " + cidades[indice]);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erro: índice inválido! Escolha um número entre 0 e 4.");
        } catch (InputMismatchException e) {
            System.out.println("Erro: digite apenas números inteiros!");
        } finally {
            System.out.println("Fim da operação");
        }

        sc.close();
    }
}
