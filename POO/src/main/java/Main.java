import dominio.*;
import interfaz.MenuBancario;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();

        MenuBancario menu = new MenuBancario(banco);

        menu.iniciar();
    }
}
