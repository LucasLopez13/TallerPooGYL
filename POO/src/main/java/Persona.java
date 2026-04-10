public class Persona {
    public String nombre;
    public int edad;
    public String direccion;

    public void caminar(){
        if (edad >=3) {
            System.out.println("Camina");
        } else {
            System.out.println("No puede caminar");
        }
    }

    public void volar(){
        if (edad >=12) {
            System.out.println("Puede volar");
        } else {
            System.out.println("No puede volar");
        }
    }

    public void esMagico(){
        if (edad >=18) {
            System.out.println("Tiene magia");
        } else {
            System.out.println("No tiene magia");
        }
    }

    public void mostrarDatos(){
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
        System.out.println("Direccion: " + direccion);
        this.esMagico();
        this.caminar();
        this.volar();
        System.out.println("----------------------");
    }
}
