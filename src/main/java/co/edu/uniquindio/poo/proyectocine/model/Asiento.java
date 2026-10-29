package co.edu.uniquindio.poo.proyectocine.model;

/** Representa un asiento dentro de una sala de cine. */
public class Asiento {
    private final String id;
    private final char fila;
    private final int numero;
    private EstadoAsiento estado;

    /** Crea un asiento con su ubicación y estado inicial. */
    public Asiento(String id, char fila, int numero, EstadoAsiento estado) {
        this.id = id; this.fila = fila; this.numero = numero;
        this.estado = estado;
    }
    /** @param estado nuevo estado del asiento */
    public void cambiarEstado(EstadoAsiento estado) { this.estado = estado; }
    /** @return {@code true} cuando el asiento puede ser seleccionado */
    public boolean estaDisponible() { return estado == EstadoAsiento.DISPONIBLE; }
    public String getId() { return id; }
    public char getFila() { return fila; }
    public int getNumero() { return numero; }
    public EstadoAsiento getEstado() { return estado; }
}
