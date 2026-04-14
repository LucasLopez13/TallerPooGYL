package dominio;

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
    private PanelAdmin panelAdmin;

    public MenuBancario(Banco banco) {
        this.banco = banco;
        this.scanner = new Scanner(System.in);
        this.procesador = new ProcesadorDeTransacciones();
        this.panelAdmin = new PanelAdmin(banco, scanner);
    }

    public void iniciar() {
        boolean salir = false;
        System.out.println("=== BIENVENIDOS AL SISTEMA BANCARIO ===");

        while (!salir) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Iniciar Sesión (Usuarios)");
            System.out.println("2. Panel de Administrador");
            System.out.println("3. Crear Nueva Cuenta");
            System.out.println("4. Salir");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    loginUsuario();
                    break;
                case 2:
                    loginAdmin();
                    break;
                case 3:
                    crearCuenta();
                    break;
                case 4:
                    salir = true;
                    System.out.println("Gracias por utilizar el sistema bancario. ¡Adios!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
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
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("\n---OPERACIONES---");
            System.out.println("1. Depositar");
            System.out.println("2. Retirar");
            System.out.println("3. Transferir");
            System.out.println("4. Consultar mi balance");
            System.out.println("5. Solicitar BAJA de mi cuenta");
            System.out.println("6. Cerrar Sesión");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    procesador.setEstrategia(new Depositar());
                    realizarTransaccion(cuentaUsuario, false);
                    break;
                case 2:
                    procesador.setEstrategia(new Retirar());
                    realizarTransaccion(cuentaUsuario, false);
                    break;
                case 3:
                    procesador.setEstrategia(new Transferir());
                    realizarTransaccion(cuentaUsuario, true);
                    break;
                case 4:
                    System.out.println("Su saldo actual es: $" + cuentaUsuario.getSaldo());
                    break;
                case 5:
                    cuentaUsuario.solicitarBaja();
                    System.out.println("Solicitud de baja enviada. Espere la respuesta del administrador.");
                    System.out.println("Por su seguridad, se cerrara la sesion");
                    cerrarSesion = true;
                    break;
                case 6:
                    cerrarSesion = true;
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
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


    private void loginAdmin() {
        System.out.print("\nContraseña de Administrador: ");
        String password = scanner.nextLine();

        if (password.equals("admin")) {
            panelAdmin.mostrarMenu();
        } else {
            System.out.println("Acceso denegado.");
        }
    }
}
