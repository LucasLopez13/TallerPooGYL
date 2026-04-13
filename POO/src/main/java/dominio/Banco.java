package dominio;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    // Lista de cuentas
    private List<Cuenta> cuentas = new ArrayList<>();

    //Registrar una nueva cuenta / sin validaciones
    public void registrarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    //Buscar una cuenta por su email
    public Cuenta buscarPorEmail(String email) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getEmail().equalsIgnoreCase(email)) {
                return cuenta;
            }
        }
        return null;
    }

    //Getter de las cuentas
    public List<Cuenta> getCuentas() {
        return cuentas;
    }
}
