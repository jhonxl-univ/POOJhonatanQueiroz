import java.util.ArrayList;

public class NotaFiscal {

    private int numero;
    private String data;
    private ArrayList<Item> listaItens;

    public NotaFiscal(int numero, String data) {
        this.numero = numero;
        this.data = data;
        this.listaItens = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<Item> getListaItens() {
        return listaItens;
    }

    public void addItem(Item item) {

        if (item == null) {
            return;
        }

        if (!listaItens.contains(item)) {
            listaItens.add(item);
        }
    }

    public void removeItem(Item item) {

        if (item == null) {
            return;
        }

        listaItens.remove(item);
    }

    public double calcularTotalNota() {

        double total = 0;

        for (Item item : listaItens) {
            total += item.calcularSubtotal();
        }

        return total;
    }

    public void listarItens() {

        System.out.println("Nota Fiscal: " + numero);
        System.out.println("Data: " + data);

        for (Item item : listaItens) {
            System.out.println(item);
        }

        System.out.printf(
                "Total da nota: R$ %.2f%n",
                calcularTotalNota()
        );
    }
}