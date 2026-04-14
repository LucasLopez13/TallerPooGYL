package interfaz;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuGenerico {
    private String titulo;
    private Scanner scanner;
    private HashMap<Integer, OpcionMenu> opciones;
    private boolean salir;

    public MenuGenerico(String titulo, Scanner scanner) {
        this.titulo = titulo;
        this.scanner = scanner;
        this.opciones = new HashMap<>();
        this.salir = false;
    }

    public void agregarOpcion(int numero, String descripcion, Runnable accion) {
        opciones.put(numero, new OpcionMenu(descripcion, accion));
    }

    public void agregarOpcionSalir(int numero, String descripcion) {
        opciones.put(numero, new OpcionMenu(descripcion, () -> this.salir = true));
    }

    public void forzarSalida() {
        this.salir = true;
    }

    public void mostrar() {
        this.salir = false;
        while(!salir) {
            System.out.println("\n---" + titulo.toUpperCase() + "---");
            for (Map.Entry<Integer,OpcionMenu> entry : opciones.entrySet()) {
                System.out.println(entry.getKey() + "." + entry.getValue().getDescripcion());
            }
            int seleccion = scanner.nextInt();
            scanner.nextLine();

            OpcionMenu opcionSeleccionada = opciones.get(seleccion);
            if (opcionSeleccionada != null) {
                opcionSeleccionada.ejecutar();
            } else {
                System.out.println("Opcion no valida.");
            }
        }
    }

    private static class OpcionMenu {
        private String descripcion;
        private Runnable accion;

        public OpcionMenu(String descripcion, Runnable accion) {
            this.descripcion = descripcion;
            this.accion = accion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void ejecutar() {
            accion.run();
        }
    }
}
