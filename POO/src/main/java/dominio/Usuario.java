package dominio;

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
