package dominio;

public class Cuenta {
    private String nombre;
    private int edad;
    private String email;
    private String direccion;
    private TipoDeCuenta tipoDeCuenta;
    private double saldo;

    public Cuenta(String nombre, int edad, String email, String direccion, TipoDeCuenta tipoDeCuenta) {
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.direccion = direccion;
        this.tipoDeCuenta = tipoDeCuenta;
        this.saldo = 0;
    }

    public void sumarSaldo(double monto) {
        this.saldo += monto;
    }

    public void restarSaldo(double monto) {
        Math.max(0.0,this.saldo - monto);
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public double getSaldo() {
        return saldo;
    }
}
