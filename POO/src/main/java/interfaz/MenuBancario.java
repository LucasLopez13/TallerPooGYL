package interfaz;

import dominio.*;
import estrategias.Depositar;
import estrategias.ProcesadorDeTransacciones;
import estrategias.Retirar;
import estrategias.Transferir;

import java.util.List;
import java.util.Scanner;

public class MenuBancario {
    private Scanner scanner;
    private Banco banco;
    private ProcesadorDeTransacciones procesador;

    public MenuBancario(Banco banco) {
        this.banco = banco;
        this.scanner = new Scanner(System.in);
        this.procesador = new ProcesadorDeTransacciones();
    }

    public void iniciar() {
        MenuGenerico menuPrincipal = new MenuGenerico("BIENVENIDOS AL SISTEMA BANCARIO", scanner);

        System.out.println("\nSeleccione una opción:");
        menuPrincipal.agregarOpcion(1,"Iniciar Sesión (Usuarios)", () -> loginUsuario());
        menuPrincipal.agregarOpcion(2,"Panel de Administrador", () -> loginAdmin()  );
        menuPrincipal.agregarOpcion(3,"Crear nueva cuenta", () -> crearCuenta());
        menuPrincipal.agregarOpcionSalir(4,"Salir");

        menuPrincipal.mostrar();
        scanner.close();
        System.out.println("Gracias por utilizar el sistema bancario. ¡Adios!");
    }

    private void loginUsuario() {
        System.out.print("\nEmail: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        Cuenta cuenta = banco.buscarPorEmailEnSucursales(email);

        if (cuenta != null && cuenta.validarPassword(password) && !cuenta.isSolicitoBaja()) {
            System.out.println("¡Bienvenido/a " + cuenta.getNombre() + "!");
            menuOperacionesUsuario(cuenta);
        } else {
            System.out.println("Error: Email o contraseña incorrectos.");
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

        System.out.print("Ingrese el monto: $");
        double monto = scanner.nextDouble();

        procesador.procesar(cuentaOrigen, emailDestino, monto, banco);
    }

    private void loginAdmin() {
        System.out.print("\nUsuario de Administrador: ");
        String adminUser = scanner.nextLine();
        System.out.print("\nContraseña de Administrador: ");
        String adminPass = scanner.nextLine();

        if (adminUser.equals("banco") && adminPass.equals("banco123")) {
            new PanelBanco(banco,scanner).iniciar();
            return;
        }

        for (Sucursal sucursal : banco.getSucursales()) {
            if (sucursal.getAdminUser().equals(adminUser) && sucursal.getAdminPassword().equals(adminPass)) {
                new PanelSucursal(sucursal,scanner).iniciar();
                return;
            }
        }

        System.out.println("Acceso denegado. Credenciales incorrectas.");
    }

    private void crearCuenta() {
        System.out.println("\nCREAR NUEVA CUENTA");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        int edad = 0;
        var esEdadValida = false;

        while (!esEdadValida) {
            System.out.print("Edad: ");
            String inputEdad = scanner.nextLine();

            try {
                edad = Integer.parseInt(inputEdad);

                if (edad > 0) {
                    esEdadValida = true;
                } else {
                    System.out.println("Debe ingresar una edad válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese una edad válida");
            }
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.println("Seleccione el tipo de cuenta (1. AHORRO, 2. CORRIENTE, 3. SUELDO): ");
        int tipoOpcion = scanner.nextInt();
        TipoDeCuenta tipo = TipoDeCuenta.AHORRO;
        if (tipoOpcion == 2) tipo = TipoDeCuenta.CORRIENTE;
        if (tipoOpcion == 3) tipo = TipoDeCuenta.SUELDO;

        System.out.println("Seleccione Sucursal:");
        List<Sucursal> sucursales = banco.getSucursales();
        for (int i = 0; i < sucursales.size(); i++) {
            System.out.println((i + 1) + ". " + sucursales.get(i).getNombre());
        }
        int sucOpcion = scanner.nextInt() - 1;
        scanner.nextLine();
        Sucursal sucursalElegida = sucursales.get(sucOpcion);

        CuentaBuilder builder = new CuentaBuilder();
        Cuenta nuevaCuenta = builder
                .conNombre(nombre)
                .conEdad(edad)
                .conEmail(email)
                .conPassword(password)
                .conDireccion(direccion)
                .conTipo(tipo)
                .conSucursal(sucursalElegida)
                .construir();

        sucursalElegida.registrarCuenta(nuevaCuenta);
        System.out.println("¡Cuenta creada exitosamente en " + sucursalElegida.getNombre() + "!");
    }
}
