package dominio;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Cuenta> cuentas = new ArrayList<>();

    public void registrarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public Cuenta buscarPorEmail(String email) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getEmail().equalsIgnoreCase(email)) {
                return cuenta;
            }
        }
        return null;
    }

}
