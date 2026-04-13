package estrategias;

import dominio.Banco;
import dominio.Cuenta;

public interface TransaccionStrategy {
    void ejecutar(Cuenta origen, String emailDestino, double monto, Banco banco);
}
