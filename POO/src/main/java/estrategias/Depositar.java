package estrategias;

import dominio.Banco;
import dominio.Cuenta;

public class Depositar implements TransaccionStrategy{
    @Override
    public void ejecutar(Cuenta origen, String emailDestino, double monto, Banco banco) {
        if (monto > 0) {
            origen.sumarSaldo(monto);
            System.out.println("Se ha depositado $" + monto + " en la cuenta de " + origen.getEmail());
        }
    }
}




