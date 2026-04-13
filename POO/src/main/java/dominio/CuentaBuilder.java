package dominio;

public class CuentaBuilder {
    private String nombre;
    private int edad;
    private String email;
    private String direccion;
    private TipoDeCuenta tipoDeCuenta;
    private double saldo;

    public CuentaBuilder conNombre(String nombre) { this.nombre = nombre; return this; }
    public CuentaBuilder conEdad(int edad) { this.edad = edad; return this; }
    public CuentaBuilder conEmail(String email) { this.email = email; return this; }
    public CuentaBuilder conDireccion(String direccion) { this.direccion = direccion; return this; }
    public CuentaBuilder conTipo(TipoDeCuenta tipo) { this.tipoDeCuenta = tipo; return this; }

    public Cuenta construir() {
        return new Cuenta(nombre, edad, email, direccion, tipoDeCuenta);
    }
}
