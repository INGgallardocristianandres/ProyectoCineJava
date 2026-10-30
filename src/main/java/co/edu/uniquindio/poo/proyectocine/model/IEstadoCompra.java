package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Interfaz que define el Patrón State para gestionar los diferentes
 * estados por los que puede pasar una compra en el sistema CinemaUQ.
 */
public interface IEstadoCompra {

    /**
     * Procesa el pago asociado a la compra según el estado actual.
     *
     * @param compra La compra sobre la cual se procesará el pago.
     */
    void procesarPago(Compra compra);

    /**
     * Cancela la compra actual según las reglas del estado en el que se encuentre.
     *
     * @param compra La compra que se desea cancelar.
     */
    void cancelar(Compra compra);
}