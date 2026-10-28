package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Política de no reembolso (devuelve 0), aplicable cuando
 * la cancelación se realiza demasiado tarde cerca de la función.
 */
public class SinRembolsoStrategy implements IEstrategiaRembolso {
    /**
     * Metodo que calcula si el rembolso es nulo
     * @param valorPagado El monto total pagado inicialmente en la compra.
     * @param horasRestantes Horas faltantes para que comience la función.
     * @return
     */
    @Override
    public double calcularRembolso(double valorPagado, double horasRestantes) {
        return 0.0;
    }
}