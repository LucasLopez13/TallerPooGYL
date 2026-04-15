package dominio;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    // Lista de cuentas
    private List<Sucursal> sucursales = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    public Banco() {
        usuarios.add(new Usuario("admin@banco.com", "banco123", Rol.ADMIN_CENTRAL));

        usuarios.add(new Usuario("boedo@banco.com", "1234", Rol.ADMIN_LOCAL));
        Sucursal boedo = new Sucursal("Sucursal Boedo", "boedo@banco.com");

        usuarios.add(new Usuario("caballito@banco.com", "1234", Rol.ADMIN_LOCAL));
        Sucursal caballito = new Sucursal("Sucursal Caballito", "caballito@banco.com");

        usuarios.add(new Usuario("once@banco.com", "1234", Rol.ADMIN_LOCAL));
        Sucursal once = new Sucursal("Sucursal Once", "once@banco.com");

        Sucursal palermo = new Sucursal("Sucursal Palermo", null);

        sucursales.add(boedo);
        sucursales.add(caballito);
        sucursales.add(once);
        sucursales.add(palermo);

        cargarDatosEnSucursales(boedo,caballito,once);
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

    public void cargarDatosEnSucursales(Sucursal boedo, Sucursal caballito, Sucursal once) {
        registrarNuevoCliente(boedo, "Lucas López", 22, "lucas@gmail.com", "1234", "San Juan 123", TipoDeCuenta.AHORRO);
        registrarNuevoCliente(once, "Sofia Martinez", 25, "sofia@gmail.com", "1234", "Avenida Boedo 456", TipoDeCuenta.CORRIENTE);
        registrarNuevoCliente(caballito, "Martin Gomez", 30, "martin@gmail.com", "1234", "Rivadavia 4500", TipoDeCuenta.SUELDO);
    }

    public void registrarNuevoCliente(Sucursal sucursal, String nombre, int edad, String email, String password, String direccion, TipoDeCuenta tipo) {
        usuarios.add(new Usuario(email, password, Rol.CLIENTE));

        Cuenta nuevaCuenta = new CuentaBuilder()
                .conNombre(nombre).conEdad(edad).conEmail(email)
                .conDireccion(direccion).conTipo(tipo).conSucursal(sucursal)
                .construir();
        sucursal.registrarCuenta(nuevaCuenta);
    }

    public void registrarNuevoAdminLocal(Sucursal sucursal, String email, String password) {
        usuarios.add(new Usuario(email, password, Rol.ADMIN_LOCAL));
        sucursal.setEmailAdmin(email);
    }

    public void mostrarAuditoriaGlobal() {
        System.out.println("---AUDITORIA GLOBAL DE LAS SUCURSALES---");
        for (Sucursal sucursal : sucursales) {
            System.out.println("Sucursal: " + sucursal.getNombre() + " | Admin local: " + sucursal.getEmailAdmin());
            System.out.println("Cuentas registradas: " + sucursal.getCuentas().size());
            System.out.println("Saldo total: $" + sucursal.consultarSaldoTotal());
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    public Sucursal buscarSucursalPorAdmin(String emailAdmin) {
        for (Sucursal s : sucursales) {
            if (s.getEmailAdmin().equalsIgnoreCase(emailAdmin)) return s;
        }
        return null;
    }

    public List<Sucursal> obtenerSucursalesSinAdmin() {
        List<Sucursal> sucursalesHuerfanas = new ArrayList<>();
        for (Sucursal s : sucursales) {
            if (s.getEmailAdmin() == null || s.getEmailAdmin().isEmpty()) {
                sucursalesHuerfanas.add(s);
            }
        }
        return sucursalesHuerfanas;
    }

    //Getter de las cuentas
    public List<Sucursal> getSucursales() {
        return sucursales;
    }
}
