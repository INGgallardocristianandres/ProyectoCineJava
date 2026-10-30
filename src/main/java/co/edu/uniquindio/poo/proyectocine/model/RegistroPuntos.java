package co.edu.uniquindio.poo.proyectocine.model;

import java.time.LocalDate;

/**
 * Representa un registro individual de puntos acumulados por un cliente,
 * controlando su fecha de obtención y vencimiento.
 *
 */
public class RegistroPuntos {
    private int puntos;
    private LocalDate fechaObtencion;
    private LocalDate fechaVencimiento;

    /**
     * Constructor para inicializar un registro de puntos.
     *
     * @param puntos Cantidad de puntos obtenidos en este registro.
     */
    public RegistroPuntos(int puntos) {
        this.puntos = puntos;
        this.fechaObtencion = LocalDate.now();
        this.fechaVencimiento = LocalDate.now().plusMonths(6);
    }

    /**
     * Verifica si el registro de puntos ha vencido respecto a la fecha actual.
     *
     * @return true si ya venció, false en caso contrario.
     */
    public boolean estaVencido() {
        return LocalDate.now().isAfter(this.fechaVencimiento);
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public LocalDate getFechaObtencion() {
        return fechaObtencion;
    }

    public void setFechaObtencion(LocalDate fechaObtencion) {
        this.fechaObtencion = fechaObtencion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
