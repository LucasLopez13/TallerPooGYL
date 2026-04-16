import dominio.*;
import interfazUsuario.PortalBancario;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();

        PortalBancario menu = new PortalBancario(banco);

        menu.iniciar();
    }
}
