public class CargaTransportadora implements Rastreavel {

    private String codigo;

    public CargaTransportadora(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getStatusRastreio() {
        return "Carga da transportadora " + codigo + ": Em transporte.";
    }
}