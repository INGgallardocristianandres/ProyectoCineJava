package co.edu.uniquindio.poo.proyectocine.model;

import java.time.LocalDateTime;

/**
 * Representa un registro detallado de cada movimiento financiero
 * realizado sobre la tarjeta virtual de un cliente.
 *
 */
public class MovimientoTarjeta {
    private String id;
    private LocalDateTime fechaMovimiento;
    private TipoMovimiento tipo;
    private double valor;
    private Administrador adminResponsable;

    /**
     * Constructor para inicializar un movimiento de tarjeta.
     *
     * @param id               Identificador único del movimiento.
     * @param fechaMovimiento  Fecha y hora en que se efectúa el movimiento.
     * @param tipo             Tipo de movimiento (ABONO o DEBITO).
     * @param valor            Cantidad monetaria involucrada.
     * @param adminResponsable Administrador que autoriza o gestiona el movimiento (si aplica).
     */
    public MovimientoTarjeta(String id, LocalDateTime fechaMovimiento, TipoMovimiento tipo, double valor, Administrador adminResponsable) {
        this.id = id;
        this.fechaMovimiento = fechaMovimiento;
        this.tipo = tipo;
        this.valor = valor;
        this.adminResponsable = adminResponsable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Administrador getAdminResponsable() {
        return adminResponsable;
    }

    public void setAdminResponsable(Administrador adminResponsable) {
        this.adminResponsable = adminResponsable;
    }
}
