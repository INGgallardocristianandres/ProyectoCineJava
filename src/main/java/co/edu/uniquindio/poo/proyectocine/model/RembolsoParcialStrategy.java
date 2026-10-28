package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Política de reembolso parcial,
 * aplicable cuando la cancelación se hace con el tiempo límite ajustado.
 *
 */
public class RembolsoParcialStrategy implements IEstrategiaRembolso {

    /**
     * Metodo para calcular el rembolso parcial
     * @param valorPagado El monto total pagado inicialmente en la compra.
     * @param horasRestantes Horas faltantes para que comience la función.
     * @return
     */
    @Override
    public double calcularRembolso(double valorPagado, double horasRestantes) {
        return valorPagado * 0.5;
    }
}