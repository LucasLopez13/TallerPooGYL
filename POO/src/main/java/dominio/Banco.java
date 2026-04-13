package dominio;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    // Lista de cuentas
    private List<Sucursal> sucursales = new ArrayList<>();

    public Banco() {
        sucursales.add(new Sucursal("Sucursal Boedo"));
        sucursales.add(new Sucursal("Sucursal Caballito"));
        sucursales.add(new Sucursal("Sucursal Once"));
    }

    //Buscar una cuenta por su email
    public Cuenta buscarPorEmailEnSucursales(String email) {
        for (Sucursal sucursal : sucursales) {
            var cuentaEncontrada = sucursal.buscarPorEmail(email);
            if (cuentaEncontrada != null) {
                return cuentaEncontrada;
            }
        }
        return null;
    }

    public double consultarSaldoTotalDelBanco() {
        double saldoTotal = 0;
        for (Sucursal sucursal : sucursales) {
            saldoTotal += sucursal.consultarSaldoTotal();
        }
        return saldoTotal;
    }

    //Getter de las cuentas
    public List<Sucursal> getSucursales() {
        return sucursales;
    }
}
