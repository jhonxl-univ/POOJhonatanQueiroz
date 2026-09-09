import java.util.ArrayList;
import java.util.List;

public class Produto implements Comparable<Produto> {

    private String codigo;
    private String nome;
    private double preco;

    // Relacionamento com Fabricante
    private Fabricante fabricante;

    // Relacionamento com Item
    private ArrayList<Item> listaItens;

    public Produto(String codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.listaItens = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public ArrayList<Item> getListaItens() {
        return listaItens;
    }

    /*
     * Mantém a associação bidirecional entre Produto e Fabricante.
     */
    public void setFabricante(Fabricante novoFab) {

        // Evita chamadas recursivas desnecessárias.
        if (this.fabricante == novoFab) {
            return;
        }

        // Se o novo fabricante for null,
        // remove o produto do fabricante antigo.
        if (novoFab == null) {

            Fabricante antigo = this.fabricante;
            this.fabricante = null;

            if (antigo != null) {
                antigo.removeProduto(this);
            }

        } else {

            // Remove do fabricante anterior, se existir.
            if (this.fabricante != null) {
                this.fabricante.removeProduto(this);
            }

            // Define o novo fabricante.
            this.fabricante = novoFab;

            // Adiciona o produto na lista do novo fabricante.
            novoFab.addProduto(this);
        }
    }

    /*
     * Métodos utilizados pela associação Produto <-> Item.
     */
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

    /*
     * Ordenação natural por nome.
     */
    @Override
    public int compareTo(Produto outro) {
        return this.nome.compareToIgnoreCase(outro.getNome());
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", preco=R$ " + String.format("%.2f", preco) +
                '}';
    }
}