package interfaz;

import dominio.Banco;
import dominio.Cuenta;
import dominio.Sucursal;

import java.util.Scanner;

public class PanelBanco extends PanelBase {
    private Banco banco;

    public PanelBanco(Banco banco, Scanner scanner) {
        super(new MenuGenerico("Panel Banco", scanner), scanner);
        this.banco = banco;
    }

    @Override
    protected void configurarOpciones() {
        menu.agregarOpcion(1, "Auditoria de sucursales y balances", () -> banco.mostrarAuditoriaGlobal() );
        menu.agregarOpcion(2, "Ver todas las cuentas", () -> listarTodoElSistema());
        menu.agregarOpcionSalir(3, "Salir");

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
