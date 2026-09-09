import java.util.Comparator;

public class ComparadorPorPreco implements Comparator<Produto> {

    @Override
    public int compare(Produto p1, Produto p2) {

        /*
         * compareTo:
         *
         * valor negativo -> p1 vem antes de p2
         * zero            -> p1 e p2 são considerados iguais
         * valor positivo  -> p1 vem depois de p2
         */

        return Double.compare(p1.getPreco(), p2.getPreco());
    }
}