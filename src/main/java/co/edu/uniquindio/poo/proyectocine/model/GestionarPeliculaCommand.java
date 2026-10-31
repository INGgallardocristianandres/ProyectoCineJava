package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Comando administrativo para gestionar una película.
 */
public class GestionarPeliculaCommand implements IComando {
    private final Administrador administrador;
    private final Pelicula pelicula;
    private final String accion;

    public GestionarPeliculaCommand(Administrador administrador, Pelicula pelicula, String accion) {
        this.administrador = administrador; this.pelicula = pelicula; this.accion = accion;
    }
    @Override public void ejecutar() {
        administrador.gestionarPelicula(pelicula, accion);
    }
    @Override public void deshacer() {
        System.out.println("Se deshizo la gestión de la película " + pelicula.getTitulo());
    }
}
