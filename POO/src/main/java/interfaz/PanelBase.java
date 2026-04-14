package interfaz;

import java.util.Scanner;

public abstract class PanelBase {
    protected MenuGenerico menu;
    protected Scanner scanner;

    public PanelBase(MenuGenerico menu, Scanner scanner) {
        this.menu = menu;
        this.scanner = scanner;
    }

    protected abstract void configurarOpciones();

    //Template Method. Reutilizamos la estructura del menu.
    public final void iniciar() {
        configurarOpciones();
        menu.mostrar();
    }
}
