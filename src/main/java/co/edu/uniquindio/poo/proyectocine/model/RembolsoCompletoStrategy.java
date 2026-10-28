package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Política de reembolso completo (devuelve el 100% del dinero),
 * aplicable usualmente cuando se cancela con una gran anticipación.
 *
 * @author Equipo CinemaUQ
 * @version 1.0
 */
public class RembolsoCompletoStrategy implements IEstrategiaRembolso {
    /**
     * Metodo que calcula el valor del Rembolso total
     * @param valorPagado El monto total pagado inicialmente en la compra.
     * @param horasRestantes Horas faltantes para que comience la función.
     * @return
     */
    @Override
    public double calcularRembolso(double valorPagado, double horasRestantes) {
        return valorPagado;
    }
}