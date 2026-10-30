package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Representa la clase base abstracta para todos los usuarios del sistema CinemaUQ.
 */
public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String correo;
    protected String contrasenia;

    /**
     * Constructor de la clase abstracta Usuario.
     *
     * @param id          Identificador único del usuario.
     * @param nombre      Nombre del usuario.
     * @param correo      Correo electrónico del usuario.
     * @param contrasenia Contraseña de acceso.
     */
    public Usuario(String id, String nombre, String correo, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    /**
     * Permite iniciar sesión validando las credenciales.
     *
     * @param correo      Correo electrónico ingresado.
     * @param contrasenia Contraseña ingresada.
     * @return true si las credenciales son correctas, false de lo contrario.
     */
    public boolean iniciarSesion(String correo, String contrasenia) {
        return this.correo.equals(correo) && this.contrasenia.equals(contrasenia);
    }

    /**
     * Cierra la sesión actual del usuario en el sistema.
     */
    public void cerrarSesion() {
        System.out.println("Sesión cerrada para el usuario: " + this.nombre);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
