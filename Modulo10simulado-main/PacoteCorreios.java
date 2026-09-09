public class PacoteCorreios implements Rastreavel {

    private String codigo;

    public PacoteCorreios(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getStatusRastreio() {
        return "Pacote dos Correios " + codigo + ": Em trânsito.";
    }
}