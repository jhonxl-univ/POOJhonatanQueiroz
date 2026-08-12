
import java.util.Scanner;

public class Exercicio4_ConversorNumerico {

    public static int converterParaInteiro(String texto) throws NumberFormatException {
        return Integer.parseInt(texto);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Digite um número: ");
            String texto = sc.nextLine();

            int numero = converterParaInteiro(texto);
            System.out.println("Número convertido com sucesso: " + numero);

        } catch (NumberFormatException e) {
            System.out.println("Erro: o texto digitado não é um número válido!");
        } finally {
            System.out.println("Fim da operação");
        }

        sc.close();
    }
}
