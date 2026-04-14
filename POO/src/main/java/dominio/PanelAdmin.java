package dominio;

import java.util.List;
import java.util.Scanner;

public class PanelAdmin {
    private Banco banco;
    private Scanner scanner;

    public PanelAdmin(Banco banco, Scanner scanner) {
        this.banco = banco;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        boolean salirAdmin = false;
        while (!salirAdmin) {
            System.out.println("\n---PANEL DE ADMINISTRADOR---");
            System.out.println("1. Ver usuarios por Sucursal");
            System.out.println("2. Gestionar solicitudes de BAJA de Cuenta");
            System.out.println("3. Consultar Balance Total del Banco");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> listarUsuariosPorSucursal();
                case 2 -> gestionarSolicitudesDeBaja();
                case 3 -> mostrarBalanceTotal();
                case 4 -> salirAdmin = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void listarUsuariosPorSucursal() {
        List<Sucursal> sucursales = banco.getSucursales();
        System.out.println("\nSeleccione Sucursal:");
        for (int i = 0; i < sucursales.size(); i++) {
            System.out.println((i + 1) + ". " + sucursales.get(i).getNombre());
        }

        int indice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indice >= 0 && indice < sucursales.size()) {
            Sucursal suc = sucursales.get(indice);
            System.out.println("\nCuentas en " + suc.getNombre() + ":");
            if (suc.getCuentas().isEmpty()) {
                System.out.println("No hay cuentas registradas.");
            } else {
                for (Cuenta c : suc.getCuentas()) {
                    System.out.println("- " + c.getNombre() + " (" + c.getEmail() + ") | Saldo: $" + c.getSaldo());
                }
            }
        } else {
            System.out.println("Sucursal no válida.");
        }
    }

    private void gestionarSolicitudesDeBaja() {
        System.out.println("Cuentas que solicitacion la baja: ");
        boolean haySolicitudes = false;
        for (Sucursal sucursal : banco.getSucursales()) {
            for (Cuenta cuenta : sucursal.getCuentas()) {
                if (cuenta.isSolicitoBaja()) {
                    haySolicitudes = true;
                    System.out.println(cuenta.getEmail() + " | " + cuenta.getNombre());
                }
            }
        }
        if (!haySolicitudes) {
            System.out.println("No hay solicitudes de baja pendientes.");
            return;
        }

        System.out.println("Ingrese el email de la cuenta para confirmar la baja:");
        var email = scanner.nextLine();
        if (!email.isEmpty()) {
            Cuenta cuentaAEliminar = banco.buscarPorEmailEnSucursales(email);
            if (cuentaAEliminar != null && cuentaAEliminar.isSolicitoBaja()) {
                cuentaAEliminar.getSucursal().eliminarCuenta(cuentaAEliminar);
            } else {
                System.out.println("La cuenta no existe o no se ha solicitado baja.");
            }
        }

    }

    private void eliminarCuenta() {
        System.out.print("\nIngrese el email de la cuenta a dar de baja: ");
        String email = scanner.nextLine();

        var cuentaAEliminar = banco.buscarPorEmailEnSucursales(email);
        if (cuentaAEliminar != null) {
            cuentaAEliminar.getSucursal().eliminarCuenta(cuentaAEliminar);
        } else {
            scanner.nextLine();
        }
    }

    private void mostrarBalanceTotal() {
        var saldoTotal = banco.consultarSaldoTotalDelBanco();
        System.out.println("El saldo total del banco es: $" + saldoTotal);
    }
}

