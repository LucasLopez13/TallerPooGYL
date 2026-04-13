package dominio;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private String nombre;
    private List<Cuenta> cuentas = new ArrayList<>();

    public Sucursal(String nombre) {
        this.nombre = nombre;
    }

    public void registrarCuenta(Cuenta cuenta) {
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

    public Cuenta buscarPorEmail(String email) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getEmail().equalsIgnoreCase(email)) {
                return cuenta;
            }
        }
        System.out.println("Cuenta no encontrada.");
        return null;
    }

    public double consultarSaldoTotal() {
        double saldoTotal = 0;
        for (Cuenta cuenta : cuentas) {
            saldoTotal += cuenta.getSaldo();
        }
        return saldoTotal;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public String getNombre() {
        return nombre;
    }
}
