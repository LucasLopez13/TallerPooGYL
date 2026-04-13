package estrategias;

import dominio.Banco;
import dominio.Cuenta;

public interface TransaccionStrategy {
    /*
    Patron de diseño Strategy para realizar transacciones
    Metodo ejecutar que van a implementar las diferentes estrategias
    */
    void ejecutar(Cuenta origen, String emailDestino, double monto, Banco banco);
}
