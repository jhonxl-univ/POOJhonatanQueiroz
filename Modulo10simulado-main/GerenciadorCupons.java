import java.util.Collection;
import java.util.Iterator;

public class GerenciadorCupons {

    public static void limparCuponsSemDesconto(
            Collection<CupomDesconto> conjuntoCupons) {

        // Iterator permite remover elementos durante a iteração
        // sem causar ConcurrentModificationException.
        Iterator<CupomDesconto> it = conjuntoCupons.iterator();

        while (it.hasNext()) {

            CupomDesconto cupom = it.next();

            if (cupom.getPorcentagem() == 0) {
                it.remove();
            }
        }
    }
}