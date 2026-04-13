package dominio;

public class Cuenta {
    private String nombre;
    private int edad;
    private String email;
    private String direccion;
    //Enum para el tipo de cuenta
    private TipoDeCuenta tipoDeCuenta;
    private double saldo;

    public Cuenta(String nombre, int edad, String email, String direccion, TipoDeCuenta tipoDeCuenta) {
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.direccion = direccion;
        this.tipoDeCuenta = tipoDeCuenta;
        //Toda cuenta nueva tiene un saldo inicial de 0
        this.saldo = 0;
    }

    //Metodos que van a usar segun la estrategia que se elija
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
