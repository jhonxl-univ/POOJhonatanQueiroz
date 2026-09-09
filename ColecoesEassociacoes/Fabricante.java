import java.util.ArrayList;

public class Fabricante {

    private String cnpj;
    private String nome;
    private ArrayList<Produto> listaProdutos;

    public Fabricante(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.listaProdutos = new ArrayList<>();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    /*
     * Adiciona um produto ao fabricante.
     */
    public void addProduto(Produto novo) {

        if (novo == null) {
            return;
        }

        // Evita duplicidade e recursão infinita.
        if (listaProdutos.contains(novo)) {
            return;
        }

        listaProdutos.add(novo);

        // Mantém a outra ponta da associação atualizada.
        novo.setFabricante(this);
    }

    /*
     * Remove um produto do fabricante.
     */
    public void removeProduto(Produto antigo) {

        if (antigo == null) {
            return;
        }

        if (!listaProdutos.contains(antigo)) {
            return;
        }

        listaProdutos.remove(antigo);

        // Atualiza a outra ponta da associação.
        antigo.setFabricante(null);
    }

    @Override
    public String toString() {
        return "Fabricante{" +
                "cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}