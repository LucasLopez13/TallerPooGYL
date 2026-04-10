public class Main {
    public static void main(String[] args) {

        Persona p = new Persona();
        p.nombre = "Julio";
        p.edad = 18;
        p.direccion = "Calle 123";
        p.mostrarDatos();

        Persona p2 = new Persona();
        p2.nombre = "Julia";
        p2.edad = 12;
        p2.direccion = "Calle 456";
        p2.mostrarDatos();

        Persona p3 = new Persona();
        p3.nombre = "Carlos";
        p3.edad = 10;
        p3.direccion = "Calle 789";
        p3.mostrarDatos();
    }
}
