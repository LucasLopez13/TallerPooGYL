package estrategias;

import dominio.Banco;
import dominio.Cuenta;

public class ProcesadorDeTransacciones {
    private TransaccionStrategy estrategia;

    public void setEstrategia(TransaccionStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void procesar(Cuenta origen, String emailDestino, double monto, Banco banco) {
        if (this.estrategia == null) {
            System.out.println("No se ha seleccionado una operación.");
            return;
        }
        this.estrategia.ejecutar(origen, emailDestino, monto, banco);
    }
}
