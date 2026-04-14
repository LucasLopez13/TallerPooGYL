package dominio;

import java.util.Scanner;

public class PanelBanco {
    private Banco banco;
    private Scanner scanner;

    private PanelBanco(Banco banco, Scanner scanner) {
        this.banco = banco;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        boolean salirBanco = false;
        while (!salirBanco) {
            System.out.println("\n---PANEL DE BANCO---");
            System.out.println("1- Auditoria de sucursales y balances");
            System.out.println("2- Ver todas las cuentas");
            System.out.println("3- Salir");
            System.out.println("Opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1 -> banco.mostrarAuditoriaGlobal();
                case 2 -> listarTodoElSistema();
                case 3 -> salirBanco = true;
                default -> System.out.println("Opcion no válida.");
            }
        }
    }

    public void listarTodoElSistema() {
        System.out.println("\n---LISTADO GLOBAL---");
        for (Sucursal sucursal : banco.getSucursales()) {
            System.out.println(sucursal.getNombre() + ": " + sucursal.consultarSaldoTotal());
            for (Cuenta cuenta : sucursal.getCuentas()) {
                System.out.println("  - " + cuenta.getEmail() + ": " + cuenta.getSaldo());
            }
        }
    }
}
