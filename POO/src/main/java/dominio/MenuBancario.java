package dominio;

import estrategias.Depositar;
import estrategias.ProcesadorDeTransacciones;
import estrategias.Retirar;
import estrategias.Transferir;

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
        boolean salir = false;
        System.out.println("BIENVENIDOS AL SISTEMA BANCARIO");

        while (!salir) {
            System.out.println("\nSeleccione una operación:");
            System.out.println("1. Crear Cuenta");
            System.out.println("2. Depositar");
            System.out.println("3. Retirar");
            System.out.println("4. Transferir");
            System.out.println("5. Consultar Balance");
            System.out.println("6. Consultar Balance Total");
            System.out.println("7. Salir");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearCuenta();
                    break;
                case 2:
                    procesador.setEstrategia(new Depositar());
                    realizarTransaccion(false);
                    break;
                case 3:
                    procesador.setEstrategia(new Retirar());
                    realizarTransaccion(false);
                    break;
                case 4:
                    procesador.setEstrategia(new Transferir());
                    realizarTransaccion(true);
                    break;
                case 5:
                    consultarBalance();
                    break;
                case 6:
                    consultarBalanceTotal();
                    break;
                case 7:
                    salir = true;
                    System.out.println("Gracias por utilizar el sistema bancario. ¡Adios!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private void crearCuenta() {
        System.out.println("\nCREAR NUEVA CUENTA");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.println("Seleccione el tipo de cuenta (1. AHORRO, 2. CORRIENTE, 3. SUELDO): ");
        int tipoOpcion = scanner.nextInt();
        TipoDeCuenta tipo = TipoDeCuenta.AHORRO;
        if (tipoOpcion == 2) tipo = TipoDeCuenta.CORRIENTE;
        if (tipoOpcion == 3) tipo = TipoDeCuenta.SUELDO;

        CuentaBuilder builder = new CuentaBuilder();
        Cuenta nuevaCuenta = builder.conNombre(nombre)
                .conEdad(edad)
                .conEmail(email)
                .conDireccion(direccion)
                .conTipo(tipo)
                .construir();

        banco.registrarCuenta(nuevaCuenta);
        System.out.println("¡Cuenta creada exitosamente para " + nombre + "!");
    }

    private void realizarTransaccion(boolean requiereDestino) {
        System.out.print("\nIngrese el email de su cuenta (Origen): ");
        String emailOrigen = scanner.nextLine();
        Cuenta cuentaOrigen = banco.buscarPorEmail(emailOrigen);

        String emailDestino = null;
        if (requiereDestino) {
            System.out.print("Ingrese el email de la cuenta DESTINO: ");
            emailDestino = scanner.nextLine();
        }

        System.out.print("Ingrese el monto: $");
        double monto = scanner.nextDouble();

        procesador.procesar(cuentaOrigen, emailDestino, monto, banco);
    }

    private void consultarBalance() {
        System.out.print("\nIngrese el email de la cuenta: ");
        String email = scanner.nextLine();
        Cuenta cuenta = banco.buscarPorEmail(email);

        if (cuenta != null) {
            System.out.println("El saldo actual de " + cuenta.getNombre() + " es: $" + cuenta.getSaldo());
        } else {
            System.out.println("Error: Cuenta no encontrada.");
        }
    }

    private void consultarBalanceTotal() {
        System.out.print("\nBalance total de todas las cuentas: ");
        var balanceTotal = 0.0;
        for (Cuenta cuenta : banco.getCuentas()) {
            balanceTotal += cuenta.getSaldo();
        }
        System.out.println("El balance total de todas las cuentas es" + ": $" + balanceTotal);
    }
}
