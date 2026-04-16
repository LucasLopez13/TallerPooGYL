package interfazUsuario;

import java.util.Scanner;

public abstract class PanelBase {
    protected MenuGenerico menu;
    protected Scanner scanner;

    public PanelBase(MenuGenerico menu, Scanner scanner) {
        this.menu = menu;
        this.scanner = scanner;
    }

    protected abstract void configurarOpciones();

    /*
     * TEMPLATE METHOD: Define el esqueleto inmutable del panel.
     * Al declarar el método como 'final', garantizamos que las clases hijas
     * (PanelAdminCentral, PanelAdminLocal) no puedan alterar el ciclo de vida:
     * siempre se configuran las opciones primero y luego se muestra el menú.
     */
    public final void iniciar() {
        configurarOpciones();
        menu.mostrar();
    }
}
