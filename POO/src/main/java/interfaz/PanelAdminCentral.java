package interfaz;

import dominio.Banco;
import dominio.Cuenta;
import dominio.Sucursal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PanelAdminCentral extends PanelBase {
    private Banco banco;

    public PanelAdminCentral(Banco banco, Scanner scanner) {
        super(new MenuGenerico("Panel Banco", scanner), scanner);
        this.banco = banco;
    }

    @Override
    protected void configurarOpciones() {
        menu.agregarOpcion(1, "Auditoria de sucursales y balances", () -> banco.mostrarAuditoriaGlobal() );
        menu.agregarOpcion(2, "Ver todas las cuentas", () -> listarTodoElSistema());
        menu.agregarOpcion(3, "Ver balance general del banco", () -> System.out.println("Balance TOTAL del banco: $" + banco.consultarSaldoTotalDelBanco()));
        menu.agregarOpcion(4, "Asignar admin a sucursal", () -> asignarNuevoAdmin());
        menu.agregarOpcionSalir(5, "Salir");

    }

    public void listarTodoElSistema() {
        System.out.println("\n---LISTADO GLOBAL---");
        for (Sucursal sucursal : banco.getSucursales()) {
            System.out.println(sucursal.getNombre() + ": " + "$" + sucursal.consultarSaldoTotal());
            for (Cuenta cuenta : sucursal.getCuentas()) {
                System.out.println("  - " + cuenta.getEmail() + ": " + "$" + cuenta.getSaldo());
            }
        }
    }

    private void asignarNuevoAdmin() {
        System.out.println("\n--- SUCURSALES SIN ADMINISTRADOR ---");

        List<Sucursal> sucursalesHuerfanas = banco.obtenerSucursalesSinAdmin();

        if (sucursalesHuerfanas.isEmpty()) {
            System.out.println("¡Excelente! Todas las sucursales tienen un administrador asignado.");
            return;
        }

        for (int i = 0; i < sucursalesHuerfanas.size(); i++) {
            System.out.println((i + 1) + ". " + sucursalesHuerfanas.get(i).getNombre());
        }

        int opcion = -1;
        boolean opcionValida = false;
        while (!opcionValida) {
            System.out.print("Seleccione el número de la sucursal: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine()) - 1;
                if (opcion >= 0 && opcion < sucursalesHuerfanas.size()) {
                    opcionValida = true;
                } else {
                    System.out.println("Error: Opción fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }

        Sucursal sucursalElegida = sucursalesHuerfanas.get(opcion);

        System.out.print("Ingrese el email del nuevo Administrador: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese una contraseña temporal: ");
        String password = scanner.nextLine();

        banco.registrarNuevoAdminLocal(sucursalElegida, email, password);

        System.out.println("¡Éxito! El administrador " + email + " ha sido asignado a " + sucursalElegida.getNombre() + ".");
    }


}
