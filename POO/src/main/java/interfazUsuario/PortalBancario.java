package interfazUsuario;

import dominio.*;
import estrategias.Depositar;
import estrategias.ProcesadorDeTransacciones;
import estrategias.Retirar;
import estrategias.Transferir;

import java.util.List;
import java.util.Scanner;

public class PortalBancario {
    private Scanner scanner;
    private Banco banco;
    private ProcesadorDeTransacciones procesador;

    public PortalBancario(Banco banco) {
        this.banco = banco;
        this.scanner = new Scanner(System.in);
        this.procesador = new ProcesadorDeTransacciones();
    }

    public void iniciar() {
        MenuGenerico menuPrincipal = new MenuGenerico("BIENVENIDOS AL SISTEMA BANCARIO", scanner);

        System.out.println("\nSeleccione una opción:");
        menuPrincipal.agregarOpcion(1, "Iniciar Sesión", () -> iniciarSesion());
        menuPrincipal.agregarOpcion(2, "Solicitar Apertura de Cuenta", () -> crearCuenta());
        menuPrincipal.agregarOpcionSalir(3, "Salir");

        menuPrincipal.mostrar();
        scanner.close();
        System.out.println("Gracias por utilizar el sistema bancario. ¡Adios!");
    }

    private void iniciarSesion() {
        System.out.print("\nEmail: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        Usuario usuario = banco.buscarUsuarioPorEmail(email);

        if (usuario != null && usuario.validarPassword(password)) {
            /*
             * Una vez autenticado exitosamente en el sistema global,
             * usamos el Rol del usuario para instanciar y derivar el flujo hacia
             * el panel (Vista) correspondiente a sus niveles de acceso.
             */
            switch (usuario.getRol()) {
                case CLIENTE:
                    Cuenta cuenta = banco.buscarPorEmailEnSucursales(usuario.getEmail());
                    if (cuenta != null && !cuenta.isSolicitoBaja()) {
                        System.out.println("\n¡Bienvenido/a " + cuenta.getNombre() + "!");
                        menuOperacionesUsuario(cuenta);
                    } else {
                        System.out.println("Su cuenta está pendiente de baja o no existe.");
                    }
                    break;

                case ADMIN_LOCAL:
                    Sucursal sucursalDelAdmin = banco.buscarSucursalPorAdmin(usuario.getEmail());
                    new PanelAdminLocal(sucursalDelAdmin, scanner).iniciar();
                    break;

                case ADMIN_CENTRAL:
                    System.out.println("\n¡Bienvenido/a ADMIN CENTRAL!");
                    new PanelAdminCentral(banco, scanner).iniciar();
                    break;
            }
        } else {
            System.out.println("Error: Credenciales incorrectas.");
        }
    }

    private void menuOperacionesUsuario(Cuenta cuentaUsuario) {
        MenuGenerico subMenu = new MenuGenerico("OPERACIONES DE: " + cuentaUsuario.getNombre(), scanner);

        subMenu.agregarOpcion(1,"Depositar", () -> {
            procesador.setEstrategia(new Depositar());
            realizarTransaccion(cuentaUsuario, false);
        });
        subMenu.agregarOpcion(2,"Retirar", () -> {
            procesador.setEstrategia(new Retirar());
            realizarTransaccion(cuentaUsuario, false);
        });
        subMenu.agregarOpcion(3,"Transferir", () -> {
            procesador.setEstrategia(new Transferir());
            realizarTransaccion(cuentaUsuario, true);
        });
        subMenu.agregarOpcion(4,"Consultar mi balance", () -> {
            System.out.println("Su saldo actual es: $" + cuentaUsuario.getSaldo());
        });
        subMenu.agregarOpcion(5, "Solicitar BAJA de mi cuenta", () -> {
            cuentaUsuario.solicitarBaja();
            System.out.println("Solicitud de baja enviada. Espere la respuesta del administrador.");
            System.out.println("Por su seguridad, se cerrara la sesion");
            subMenu.forzarSalida();
        });
        subMenu.agregarOpcionSalir(6, "Salir");
        subMenu.mostrar();
    }

    private void realizarTransaccion(Cuenta cuentaOrigen, boolean requiereDestino) {
        String emailDestino = null;
        if (requiereDestino) {
            System.out.print("Ingrese el email de la cuenta destino: ");
            emailDestino = scanner.nextLine();
            if (banco.buscarPorEmailEnSucursales(emailDestino) == null) {
                System.out.println("Error: La cuenta destino no existe.");
                return;
            }
        }

        double monto = LectorConsola.leerDoublePositivo(scanner, "Ingrese el monto: $");
        procesador.procesar(cuentaOrigen, emailDestino, monto, banco);
    }

    private void crearCuenta() {
        System.out.println("\nCREAR NUEVA CUENTA");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        int edad = LectorConsola.leerEnteroEnRango(scanner, "Edad: ", 1, 120);

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        int opcion = LectorConsola.leerEnteroEnRango(scanner,
                "Seleccione el tipo de cuenta (1. AHORRO, 2. CORRIENTE, 3. SUELDO): ", 1, 3);

        TipoDeCuenta tipo = TipoDeCuenta.AHORRO;
        if (opcion == 2) tipo = TipoDeCuenta.CORRIENTE;
        if (opcion == 3) tipo = TipoDeCuenta.SUELDO;

        System.out.println("\nSeleccione Sucursal:");
        List<Sucursal> sucursales = banco.getSucursales();
        for (int i = 0; i < sucursales.size(); i++) {
            System.out.println((i + 1) + ". " + sucursales.get(i).getNombre());
        }

        int sucOpcion = LectorConsola.leerEnteroEnRango(scanner, "Opción: ", 1, sucursales.size()) - 1;
        Sucursal sucursalElegida = sucursales.get(sucOpcion);

        banco.registrarNuevoCliente(sucursalElegida, nombre, edad, email, password, direccion, tipo);

        System.out.println("¡Cuenta creada exitosamente en " + sucursalElegida.getNombre() + "!");
    }
}
