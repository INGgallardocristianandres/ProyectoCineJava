package co.edu.uniquindio.poo.proyectocine.model;
/**
 * Representa al usuario administrador del sistema CinemaUQ,
 * encargado de gestionar películas, funciones, tarjetas y reportes.
 *
 */
public class Administrador extends Usuario {
    private String nivelAcceso;

    /**
     * Constructor del Administrador.
     *
     * @param id Identificador único.
     * @param nombre Nombre del administrador.
     * @param correo Correo electrónico.
     * @param contrasenia Contraseña.
     * @param nivelAcceso Nivel de privilegios del administrador.
     */
    public Administrador(String id, String nombre, String correo, String contrasenia, String nivelAcceso) {
        super(id, nombre, correo, contrasenia);
        this.nivelAcceso = nivelAcceso;
    }

    /**
     * Gestiona el catálogo de películas (agregar, modificar, eliminar).
     *
     * @param pelicula Objeto película a gestionar.
     * @param accion Descripción de la acción a realizar.
     */
    public void gestionarPelicula(Pelicula pelicula, String accion) {
        System.out.println("Administrador " + getNombre() + " gestionando película " + accion + " ");
    }

    /**
     * Gestiona la programación de funciones en el cine.
     *
     * @param funcion Objeto función a gestionar.
     * @param accion Descripción de la acción a realizar.
     */
    public void gestionarFuncion(Funcion funcion, String accion) {
        System.out.println("Administrador " + getNombre() + " gestionando función" + accion + " ");
    }

    /**
     * Recarga saldo a la tarjeta virtual de un cliente.
     *
     * @param cliente Cliente receptor de la recarga.
     * @param valor Monto a recargar.
     */
    public void recargarTarjetaCliente(Cliente cliente, double valor) {
        if (cliente != null && cliente.getTarjetaVirtual() != null) {
            cliente.getTarjetaVirtual().abonar(valor);
            System.out.println("Recarga de " + valor + " exitosa para el cliente: " + cliente.getNombre());
        }
    }

    /**
     * Cancela una función programada en el sistema.
     *
     * @param funcion Función a cancelar.
     */
    public void cancelarFuncion(Funcion funcion) {
        System.out.println("Función cancelada correctamente por el administrador.");
    }

    /**
     * Genera reportes administrativos del sistema.
     *
     * @param tipo Tipo de reporte (ej. Ventas, Asistencia).
     * @param formato Formato de salida (ej. PDF, Excel).
     */
    public void generarReporte(String tipo, String formato) {
        System.out.println("Generando reporte de " + tipo + " en formato " + formato);
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }
}
