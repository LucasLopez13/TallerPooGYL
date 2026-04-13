package estrategias;

import dominio.Banco;
import dominio.Cuenta;

public class ProcesadorDeTransacciones {
    //El usuario elije la estrategia que quiere usar
    private TransaccionStrategy estrategia;

    //La setea aca para que no se repita en cada metodo
    public void setEstrategia(TransaccionStrategy estrategia) {
        this.estrategia = estrategia;
    }

    //Llama a la estrategia que elija y la procesa
    public void procesar(Cuenta origen, String emailDestino, double monto, Banco banco) {
        if (this.estrategia == null) {
            System.out.println("No se ha seleccionado una operación.");
            return;
        }
        this.estrategia.ejecutar(origen, emailDestino, monto, banco);
    }
}
