package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Sala de cine y sus asientos. */
public class Sala {
    private final int numeroSala;
    private final int capacidad;
    private final List<Asiento> asientos;

    /** Crea una sala e inicializa sus asientos disponibles. */
    public Sala(int numeroSala, int capacidad) {
        if (capacidad < 0) throw new IllegalArgumentException("La capacidad no puede ser negativa");
        this.numeroSala = numeroSala; this.capacidad = capacidad;
        this.asientos = new ArrayList<>();
        inicializarAsientos();
    }
    /** @return {@code true} si existe al menos un asiento disponible */
    public boolean verificarDisponibilidad() { return asientos.stream().anyMatch(Asiento::estaDisponible); }
    /** Llena la sala con asientos disponibles sin duplicarlos. */
    public void inicializarAsientos() {
        if (!asientos.isEmpty()) return;
        for (int i = 1; i <= capacidad; i++) {
            asientos.add(new Asiento("S" + numeroSala + "-" + i, 'A', i, EstadoAsiento.DISPONIBLE));
        }
    }
    public int getNumeroSala() { return numeroSala; }
    public int getCapacidad() { return capacidad; }
    public List<Asiento> getAsientos() { return Collections.unmodifiableList(asientos); }
}
