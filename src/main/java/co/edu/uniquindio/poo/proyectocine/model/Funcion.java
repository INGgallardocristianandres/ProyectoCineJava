package co.edu.uniquindio.poo.proyectocine.model;

import java.time.LocalDateTime;

/** Función programada de una película en una sala. */
public class Funcion {
    private final String id;
    private final LocalDateTime fechaHora;
    private final double precioEntrada;
    private final Pelicula pelicula;
    private final Sala sala;
    private boolean activo;

    /** Crea una función cinematográfica. */
    public Funcion(String id, LocalDateTime fechaHora, double precioEntrada,
                   Pelicula pelicula, Sala sala, boolean activo) {
        this.id = id; this.fechaHora = fechaHora; this.precioEntrada = precioEntrada;
        this.pelicula = pelicula; this.sala = sala; this.activo = activo;
    }
    /** @return {@code true} si la sala tiene asientos disponibles */
    public boolean verificarAsientosDisponibles() { return sala != null && sala.verificarDisponibilidad(); }
    /** Activa la función si cuenta con asientos disponibles. */
    public void programarFuncion() { activo = verificarAsientosDisponibles(); }
    public String getId() { return id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public double getPrecioEntrada() { return precioEntrada; }
    public Pelicula getPelicula() { return pelicula; }
    public Sala getSala() { return sala; }
    public boolean isActivo() { return activo; }
}
