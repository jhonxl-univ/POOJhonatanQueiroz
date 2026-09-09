import java.util.ArrayList;

public class Categoria {

    private int id;
    private String descricao;
    private ArrayList<Produto> listaProdutos;

    public Categoria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.listaProdutos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void adicionarProduto(Produto p) {

        if (p == null) {
            return;
        }

        if (!listaProdutos.contains(p)) {
            listaProdutos.add(p);
        }
    }

    public void removerProduto(Produto p) {

        if (p == null) {
            return;
        }

        listaProdutos.remove(p);
    }

    public void listarProdutos() {

        System.out.println("Categoria: " + descricao);

        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : listaProdutos) {
            System.out.println(produto);
        }
    }
}