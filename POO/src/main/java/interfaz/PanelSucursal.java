package interfaz;

import dominio.Cuenta;
import dominio.Sucursal;

import java.util.Scanner;

public class PanelSucursal extends PanelBase{
    private Sucursal sucursal;

    public PanelSucursal(Sucursal sucursal, Scanner scanner) {
        super(new MenuGenerico("PANEL SUCURSAL - " + sucursal.getNombre(),scanner),scanner);
        this.sucursal = sucursal;
    }

    @Override
    protected void configurarOpciones() {
        menu.agregarOpcion(1, "Listar mis usuarios y balances", () -> listarUsuariosLocales());
        menu.agregarOpcion(2, "Procesar bajas pendientes", () -> gestionarBajasLocales());
        menu.agregarOpcion(3, "Balance general de la sucursal", () -> System.out.println("Balance TOTAL de la sucursal: $" + sucursal.consultarSaldoTotal()));
        menu.agregarOpcionSalir(4, "Salir");

    }

    public void listarUsuariosLocales() {
        System.out.println("\nCuentas en " + sucursal.getNombre() + ":");
        if (sucursal.getCuentas().isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }
        for (Cuenta c : sucursal.getCuentas()) {
            System.out.println("- " + c.getNombre() + " (" + c.getEmail() + ") | Saldo: $" + c.getSaldo());
        }
    }

    public void gestionarBajasLocales() {
        System.out.println("Cuentas que solicitacion la baja en sucursal " + sucursal.getNombre() + ":");
        boolean haySolicitudes = false;
        for (Cuenta cuenta : sucursal.getCuentas()) {
            if (cuenta.isSolicitoBaja()) {
                haySolicitudes = true;
                System.out.println(cuenta.getEmail() + " | " + cuenta.getNombre());
            }
        }
        if (!haySolicitudes) {
            System.out.println("No hay solicitudes de baja pendientes.");
            return;
        }

        System.out.println("Ingrese el email de la cuenta para confirmar la baja:");
        var email = scanner.nextLine();
        if (!email.isEmpty()) {
            Cuenta cuentaAEliminar = sucursal.buscarPorEmail(email);
            if (cuentaAEliminar != null && cuentaAEliminar.isSolicitoBaja()) {
                sucursal.eliminarCuenta(cuentaAEliminar);
            } else {
                System.out.println("La cuenta no existe o no se ha solicitado baja.");
            }
        }
    }


}
