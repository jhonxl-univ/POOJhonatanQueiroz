public class Item {

    private int quantidade;
    private double precoVendaUnitario;

    private NotaFiscal notaFiscal;
    private Produto produto;

    public Item(
            NotaFiscal nf,
            Produto prod,
            int quantidade,
            double precoVendaUnitario) {

        this.quantidade = quantidade;
        this.precoVendaUnitario = precoVendaUnitario;
        this.notaFiscal = nf;
        this.produto = prod;

        // Ao criar o item, ele é vinculado às duas entidades.
        if (nf != null) {
            nf.addItem(this);
        }

        if (prod != null) {
            prod.addItem(this);
        }
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoVendaUnitario() {
        return precoVendaUnitario;
    }

    public void setPrecoVendaUnitario(double precoVendaUnitario) {
        this.precoVendaUnitario = precoVendaUnitario;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public Produto getProduto() {
        return produto;
    }

    public double calcularSubtotal() {
        return quantidade * precoVendaUnitario;
    }

    @Override
    public String toString() {

        String nomeProduto =
                produto != null ? produto.getNome() : "Produto não informado";

        return "Item{" +
                "produto='" + nomeProduto + '\'' +
                ", quantidade=" + quantidade +
                ", precoUnitario=R$ " +
                String.format("%.2f", precoVendaUnitario) +
                ", subtotal=R$ " +
                String.format("%.2f", calcularSubtotal()) +
                '}';
    }
}