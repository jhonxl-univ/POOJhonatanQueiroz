public class CentralRastreamento {

    public static void inspecionarItem(Object item) {

        // Verifica se o objeto implementa a interface Rastreavel
        if (item instanceof Rastreavel) {

            // Casting explícito do objeto para Rastreavel
            Rastreavel rastreavel = (Rastreavel) item;

            System.out.println("Status: " + rastreavel.getStatusRastreio());

        } else {

            System.out.println("O item não é passível de rastreamento.");
        }
    }
}