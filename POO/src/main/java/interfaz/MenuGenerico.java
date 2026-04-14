package interfaz;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuGenerico {
    private String titulo;
    private Scanner scanner;
    private Map<Integer, OpcionMenu> opciones;
    private boolean salir;

    public MenuGenerico(String titulo, Scanner scanner) {
        this.titulo = titulo;
        this.scanner = scanner;
        this.opciones = new LinkedHashMap<>();
        this.salir = false;
    }

    /*
    Patron Command para agregar opciones, mediante la interfaz Runnable.
    Cada vez que se utilize este metodo convierte la accion que el usuario
    quiere realizar en un objeto y este se guarda en el mapa Opciones.
     */
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
            System.out.print("\nSeleccione una opcion: ");
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

    //Clase interna para almacenar las opciones y su descripcion. Ademas de el metodo para ejecutarlas.
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
