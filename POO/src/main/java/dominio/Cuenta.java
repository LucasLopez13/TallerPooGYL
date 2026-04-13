package dominio;

public class Cuenta {
    private String nombre;
    private int edad;
    private String email;
    private String password;
    private String direccion;
    //Enum para el tipo de cuenta
    private TipoDeCuenta tipoDeCuenta;
    private double saldo;
    private Sucursal sucursal;

    public Cuenta(String nombre, int edad, String email, String password, String direccion, TipoDeCuenta tipoDeCuenta, Sucursal sucursal) {
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.tipoDeCuenta = tipoDeCuenta;
        this.sucursal = sucursal;
        //Toda cuenta nueva tiene un saldo inicial de 0
        this.saldo = 0;
    }

    //Metodos que van a usar segun la estrategia que se elija
    public void sumarSaldo(double monto) {
        this.saldo += monto;
    }

    public void restarSaldo(double monto) {
        Math.max(0.0,this.saldo = this.saldo - monto);
    }

    public boolean esContraseñaCorrecta(String password) {
        return this.password.equals(password);
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
