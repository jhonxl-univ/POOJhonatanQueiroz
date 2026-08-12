
import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercicio1_DivisaoSegura {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Digite o primeiro número (dividendo): ");
            int a = sc.nextInt();

            System.out.print("Digite o segundo número (divisor): ");
            int b = sc.nextInt();

            int resultado = a / b;
            System.out.println("Resultado da divisão: " + resultado);

        } catch (ArithmeticException e) {
            System.out.println("Erro: não é possível dividir por zero!");
        } catch (InputMismatchException e) {
            System.out.println("Erro: digite apenas números inteiros!");
        } finally {
            System.out.println("Fim da operação");
        }

        sc.close();
    }
}
