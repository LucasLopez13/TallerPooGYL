import dominio.*;
import interfaz.PortalBancario;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();

        PortalBancario menu = new PortalBancario(banco);

        menu.iniciar();
    }
}
