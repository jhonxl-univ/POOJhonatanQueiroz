import java.util.Comparator;

public class ComparadorPorCodigo implements Comparator<Produto> {

    @Override
    public int compare(Produto p1, Produto p2) {
        return p1.getCodigo().compareToIgnoreCase(p2.getCodigo());
    }
}