package dominio;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private String nombre;
    private String emailAdmin;
    private List<Cuenta> cuentas = new ArrayList<>();

    public Sucursal(String nombre, String emailAdmin) {
        this.nombre = nombre;
        this.emailAdmin = emailAdmin;
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
        return null;
    }

    public double consultarSaldoTotal() {
        double saldoTotal = 0;
        for (Cuenta cuenta : cuentas) {
            saldoTotal += cuenta.getSaldo();
        }
        return saldoTotal;

    }

    public List<Cuenta>obtenerSolicitudesDeBajas() {
        List<Cuenta> cuentasConSolicitud = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.isSolicitoBaja()) {
                cuentasConSolicitud.add(cuenta);
            }
        }
        return cuentasConSolicitud;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }
}
