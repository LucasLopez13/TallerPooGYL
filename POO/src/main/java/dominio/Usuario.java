package dominio;

/*
 * Aplicamos el principio de Composición sobre Herencia. Esta clase tiene Responsabilidad
 * Única: manejar la seguridad (credenciales y permisos). La relación con sus cuentas
 * bancarias o sucursales se hace de forma relacional utilizando el 'email'.
 */
public class Usuario {
    private String email;
    private String password;
    private Rol rol;

    public Usuario(String email, String password, Rol rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public boolean validarPassword(String password) {
        return this.password.equals(password);
    }

    public String getEmail() {
        return email;
    }

    public Rol getRol() {
        return rol;
    }
}
