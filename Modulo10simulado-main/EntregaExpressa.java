public class EntregaExpressa implements Rastreavel {

    private String codigo;

    public EntregaExpressa(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getStatusRastreio() {
        return "Entrega expressa " + codigo + ": Saiu para entrega.";
    }
}