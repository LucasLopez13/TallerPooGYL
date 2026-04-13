package dominio;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    // Lista de cuentas
    private List<Sucursal> sucursales = new ArrayList<>();

    public Banco() {
        Sucursal boedo = new Sucursal("Sucursal Boedo");
        Sucursal caballito = new Sucursal("Sucursal Caballito");
        Sucursal once = new Sucursal("Sucursal Once");

        sucursales.add(boedo);
        sucursales.add(caballito);
        sucursales.add(caballito);

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

    //Getter de las cuentas
    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void cargarDatosEnSucursales(Sucursal boedo, Sucursal caballito, Sucursal once) {
        boedo.registrarCuenta(new CuentaBuilder()
                .conNombre("Lucas López")
                .conEdad(22)
                .conEmail("lucas@gmail.com")
                .conPassword("1234")
                .conDireccion("San Juan 123")
                .conTipo(TipoDeCuenta.AHORRO)
                .conSucursal(boedo)
                .construir());

        boedo.registrarCuenta(new CuentaBuilder()
                .conNombre("Sofia Martinez")
                .conEdad(25)
                .conEmail("sofia@gmail.com")
                .conPassword("1234")
                .conDireccion("Avenida Boedo 456")
                .conTipo(TipoDeCuenta.CORRIENTE)
                .conSucursal(boedo)
                .construir());

        caballito.registrarCuenta(new CuentaBuilder()
                .conNombre("Martin Gomez")
                .conEdad(30)
                .conEmail("martin@gmail.com")
                .conPassword("1234")
                .conDireccion("Rivadavia 4500")
                .conTipo(TipoDeCuenta.SUELDO)
                .conSucursal(caballito)
                .construir());

        caballito.registrarCuenta(new CuentaBuilder()
                .conNombre("Valeria Sanchez")
                .conEdad(28)
                .conEmail("valeria@gmail.com")
                .conPassword("1234")
                .conDireccion("Acoyte 120")
                .conTipo(TipoDeCuenta.AHORRO)
                .conSucursal(caballito)
                .construir());

        once.registrarCuenta(new CuentaBuilder()
                .conNombre("Diego Torres")
                .conEdad(45)
                .conEmail("diego@gmail.com")
                .conPassword("1234")
                .conDireccion("Corrientes 2500")
                .conTipo(TipoDeCuenta.CORRIENTE)
                .conSucursal(once)
                .construir());

        once.registrarCuenta(new CuentaBuilder()
                .conNombre("Camila Fernandez")
                .conEdad(33)
                .conEmail("camila@gmail.com")
                .conPassword("1234")
                .conDireccion("Pueyrredon 300")
                .conTipo(TipoDeCuenta.SUELDO)
                .conSucursal(once)
                .construir());
    }
}
