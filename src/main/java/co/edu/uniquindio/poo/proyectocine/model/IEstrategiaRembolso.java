package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Interfaz que define la estrategia para calcular el reembolso
 * de una compra según las políticas del cine CinemaUQ.
 *
 */
public interface IEstrategiaRembolso {

    /**
     * Calcula el valor a reembolsar al cliente basado en el valor pagado
     * y el tiempo restante para la función.
     *
     * @param valorPagado El monto total pagado inicialmente en la compra.
     * @param horasRestantes Horas faltantes para que comience la función.
     * @return El valor exacto que se le devolverá al cliente.
     */
    double calcularRembolso(double valorPagado, double horasRestantes);
}