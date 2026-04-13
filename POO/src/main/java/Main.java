import dominio.Banco;
import dominio.Cuenta;
import dominio.CuentaBuilder;
import dominio.TipoDeCuenta;
import estrategias.Depositar;
import estrategias.TransaccionStrategy;

import java.util.Scanner;
public class Main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Banco banco = new Banco();
            boolean salir = false;

            System.out.println("BIENVENIDOS AL SISTEMA BANCARIO");

            while (!salir) {
                System.out.println("\nSeleccione una operación:");
                System.out.println("1. Crear Cuenta");
                System.out.println("2. Depositar");
                System.out.println("3. Retirar");
                System.out.println("4. Transferir");
                System.out.println("5. Consultar Balance");
                System.out.println("6. Salir");
                System.out.print("Opción: ");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        crearCuenta(scanner, banco);
                        break;
                    case 2:
                        realizarTransaccion(scanner, banco, new Depositar(), false);
                        break;
                    case 3:
                        System.out.println("Funcionalidad de Retiro en construcción...");
                        break;
                    case 4:
                        System.out.println("Funcionalidad de Transferencia en construcción...");
                        break;
                    case 5:
                        consultarBalance(scanner, banco);
                        break;
                    case 6:
                        salir = true;
                        System.out.println("Gracias por utilizar el sistema bancario. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            }
            scanner.close();
        }

        // --- MÉTODOS AUXILIARES PARA MANTENER EL MAIN LIMPIO ---

        private static void crearCuenta(Scanner scanner, Banco banco) {
            System.out.println("\n--- CREAR NUEVA CUENTA ---");
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Edad: ");
            int edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();

            System.out.println("Seleccione el tipo de cuenta (1. AHORRO, 2. CORRIENTE, 3. SUELDO): ");
            int tipoOpcion = scanner.nextInt();
            TipoDeCuenta tipo = TipoDeCuenta.AHORRO; // Por defecto
            if (tipoOpcion == 2) tipo = TipoDeCuenta.CORRIENTE;
            if (tipoOpcion == 3) tipo = TipoDeCuenta.SUELDO;

            // Utilizamos el patrón Builder
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

        private static void realizarTransaccion(Scanner scanner, Banco banco, TransaccionStrategy estrategia, boolean requiereDestino) {
            System.out.print("\nIngrese el email de su cuenta (Origen): ");
            String emailOrigen = scanner.nextLine();
            Cuenta cuentaOrigen = banco.buscarPorEmail(emailOrigen);

            if (cuentaOrigen == null) {
                System.out.println("Error: No se encontró ninguna cuenta con ese email.");
                return;
            }

            String emailDestino = null;
            if (requiereDestino) {
                System.out.print("Ingrese el email de la cuenta DESTINO: ");
                emailDestino = scanner.nextLine();
            }

            System.out.print("Ingrese el monto: $");
            double monto = scanner.nextDouble();

            // Se ejecuta la estrategia elegida
            estrategia.ejecutar(cuentaOrigen, emailDestino, monto,banco);
        }

        private static void consultarBalance(Scanner scanner, Banco banco) {
            System.out.print("\nIngrese el email de la cuenta: ");
            String email = scanner.nextLine();
            Cuenta cuenta = banco.buscarPorEmail(email);

            if (cuenta != null) {
                System.out.println("El saldo actual de " + cuenta.getNombre() + " es: $" + cuenta.getSaldo());
            } else {
                System.out.println("Error: Cuenta no encontrada.");
            }
        }
    }
