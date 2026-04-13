package dominio;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private String nombre;
    private List<Cuenta> cuentas = new ArrayList<>();

    public Sucursal(String nombre, List<Cuenta> cuentas) {
        this.nombre = nombre;
        this.cuentas = cuentas;
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public void eliminarCuenta(Cuenta cuenta) {
        if (cuentas.contains(cuenta)) {
            cuentas.remove(cuenta);
            System.out.println("Cuenta eliminada exitosamente.");
        } else {
            System.out.println("La cuenta no se encuentra en la sucursal.");
        }

    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public String getNombre() {
        return nombre;
    }
}
