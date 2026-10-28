package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Representa una película proyectada en las salas de CinemaUQ,
 * incluyendo su información general, duración y clasificación.
 */
public class Pelicula {

    private String id;
    private String titulo;
    private GeneroPelicula genero;
    private int duracionMinutos;
    private ClasificacionPelicula clasificacion;

    /**
     * Constructor para inicializar una nueva película.
     *
     * @param id              Identificador único de la película.
     * @param titulo          Título o nombre de la película.
     * @param genero          Género cinematográfico.
     * @param duracionMinutos Duración total en minutos.
     * @param clasificacion   Clasificación por edades.
     */
    public Pelicula(String id, String titulo, GeneroPelicula genero, int duracionMinutos, ClasificacionPelicula clasificacion) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
    }

    /**
     * Actualiza la información básica de la película.
     *
     * @param nuevoTitulo Nuevo título a asignar.
     * @param nuevoGenero Nuevo género cinematográfico.
     */
    public void actualizarInfo(String nuevoTitulo, GeneroPelicula nuevoGenero) {
        if (nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) {
            this.titulo = nuevoTitulo;
        }
        if (nuevoGenero != null) {
            this.genero = nuevoGenero;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public GeneroPelicula getGenero() {
        return genero;
    }

    public void setGenero(GeneroPelicula genero) {
        this.genero = genero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public ClasificacionPelicula getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasificacionPelicula clasificacion) {
        this.clasificacion = clasificacion;
    }
}