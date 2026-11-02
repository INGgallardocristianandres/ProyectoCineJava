package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Administra el saldo total de puntos del cliente y la lista
 * de registros individuales acumulados.
 */
public class CuentaPuntos {
    private int saldoTotal;
    private List<RegistroPuntos> registroPuntos;

    /**
     * Constructor para inicializar la cuenta de puntos.
     *
     * @param saldoTotal Saldo inicial de puntos.
     */
    public CuentaPuntos(int saldoTotal) {
        this.saldoTotal = saldoTotal;
        this.registroPuntos = new ArrayList<>();
    }

    /**
     * Intenta gastar o descontar puntos de la cuenta si el saldo es suficiente.
     *
     * @param puntosRequeridos Cantidad de puntos que se desean gastar.
     * @return true si la operación fue exitosa, false si no hay suficientes puntos.
     */
    public boolean gastarPuntos(int puntosRequeridos) {
        // Primero eliminamos los que ya vencieron para estar seguros
        limpiarPuntosVencidos();

        if (this.saldoTotal < puntosRequeridos || puntosRequeridos <= 0) {
            return false;
        }
        int puntosPorDescontar = puntosRequeridos;
        registroPuntos.sort(Comparator.comparing(RegistroPuntos::getFechaVencimiento));
        List<RegistroPuntos> registrosAAgotar = new ArrayList<>();
        for (RegistroPuntos registro : registroPuntos) {
            if (puntosPorDescontar <= 0) break;
            if (registro.getPuntos() <= puntosPorDescontar) {
                puntosPorDescontar -= registro.getPuntos();
                registrosAAgotar.add(registro);
            } else {
                registro.setPuntos(registro.getPuntos() - puntosPorDescontar);
                puntosPorDescontar = 0;
            }
        }
        registroPuntos.removeAll(registrosAAgotar);
        this.saldoTotal -= puntosRequeridos;
        return true;
    }



    /**
     * Acumula una nueva cantidad de puntos generando un registro asociado.
     *
     * @param puntos Cantidad de puntos a acumular.
     */
    public void acumularPuntos(int puntos) {
        if (puntos > 0) {
            this.saldoTotal += puntos;
            this.registroPuntos.add(new RegistroPuntos(puntos));
        }
    }

    /**
     * Limpia y remueve los puntos que ya se encuentren vencidos, actualizando el saldo total.
     */
    public void limpiarPuntosVencidos() {
        List<RegistroPuntos> vencidos = new ArrayList<>();
        for (RegistroPuntos registroPuntos1 : this.registroPuntos) {
            if (registroPuntos1.estaVencido()) {
                vencidos.add(registroPuntos1);
                this.saldoTotal -= registroPuntos1.getPuntos();
            }
        }
        this.registroPuntos.removeAll(vencidos);
    }

    /**
     * Método auxiliar compatible con el llamado de la clase Cliente.
     *
     * @return El saldo total de puntos actuales.
     */
    public int getCantidadPuntos(){limpiarPuntosVencidos();
        return this.saldoTotal;
    }

    /**
     * Método auxiliar compatible con el descuento directo de puntos.
     *
     * @param cantidad Cantidad a descontar.
     */
    public void descontarPuntos(int cantidad) {
        gastarPuntos(cantidad);
    }

    public int getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(int saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public List<RegistroPuntos> getRegistroPuntos() {
        return registroPuntos;
    }

    public void setRegistroPuntos(List<RegistroPuntos> registroPuntos) {
        this.registroPuntos = registroPuntos;
    }
}