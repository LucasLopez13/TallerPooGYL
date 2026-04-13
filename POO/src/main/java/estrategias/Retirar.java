package estrategias;

import dominio.Banco;
import dominio.Cuenta;

public class Retirar implements TransaccionStrategy{
    @Override
    public void ejecutar(Cuenta origen, String emailDestino, double monto, Banco banco) {
        if (monto > 0 && monto <= origen.getSaldo()) {
            origen.restarSaldo(monto);
            System.out.println("Se ha retirado $" + monto + " de la cuenta de " + origen.getEmail());
        } else {
            System.out.println("Saldo insuficiente para realizar la retirada.");
        }
    }
}
