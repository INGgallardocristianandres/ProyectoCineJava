package co.edu.uniquindio.poo.proyectocine.model;

/** Película asociada a una función. */
public class Pelicula {
    private final String titulo;

    /** @param titulo título de la película */
    public Pelicula(String titulo) { this.titulo = titulo; }
    /** @return título de la película */
    public String getTitulo() { return titulo; }
}
