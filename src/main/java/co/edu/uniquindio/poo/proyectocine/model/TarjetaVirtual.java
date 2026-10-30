package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la tarjeta virtual asociada a un cliente en el sistema CinemaUQ,
 * permitiendo gestionar saldos, estados y el historial de movimientos.
 */
public class TarjetaVirtual {
    private String numeroTarjeta;
    private double saldo;
    private EstadoTarjeta estado;
    private List<MovimientoTarjeta> movimientos;

    /**
     * Constructor para inicializar la tarjeta virtual.
     *
     * @param numeroTarjeta Número único de identificación de la tarjeta.
     * @param saldo         Saldo inicial de la tarjeta.
     * @param estado        Estado actual de la tarjeta.
     */
    public TarjetaVirtual(String numeroTarjeta, double saldo, EstadoTarjeta estado) {
        this.numeroTarjeta = numeroTarjeta;
        this.saldo = saldo;
        this.estado = estado;
        this.movimientos = new ArrayList<>();
    }

    /**
     * Abona (recarga) un monto de dinero a la tarjeta virtual.
     *
     * @param monto Cantidad de dinero a abonar.
     */
    public void abonar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    /**
     * Debita un monto de dinero de la tarjeta si hay saldo suficiente.
     *
     * @param monto Cantidad de dinero a debitar.
     * @return true si la transacción fue exitosa, false en caso contrario.
     */
    public boolean debitar(double monto) {
        if (monto > 0 && this.saldo >= monto) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    /**
     * Consulta el saldo actual disponible en la tarjeta virtual.
     *
     * @return El saldo actual.
     */
    public double consultarSaldo() {
        return this.saldo;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public EstadoTarjeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarjeta estado) {
        this.estado = estado;
    }

    public List<MovimientoTarjeta> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoTarjeta> movimientos) {
        this.movimientos = movimientos;
    }
}