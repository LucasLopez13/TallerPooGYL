package estrategias;

import dominio.Banco;
import dominio.Cuenta;

public class Transferir implements TransaccionStrategy{
    @Override
    public void ejecutar(Cuenta origen, String emailDestino, double monto, Banco banco) {
        if (monto > 0 && monto <= origen.getSaldo()) {
            origen.restarSaldo(monto);
            banco.buscarPorEmail(emailDestino).sumarSaldo(monto);
        } else {
            System.out.println("Saldo insuficiente para realizar la transferencia.");
        }
    }
}
