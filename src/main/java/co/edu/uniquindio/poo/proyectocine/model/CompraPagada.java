package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Estado de una compra cuyo pago fue aprobado.
 */
public class CompraPagada implements IEstadoCompra {
    @Override
    public void procesarPago(Compra compra) {
        throw new IllegalStateException("La compra ya fue pagada");
    }

    @Override
    public void cancelar(Compra compra) {
        if (compra == null) throw new IllegalArgumentException("La compra es obligatoria");
        compra.cambiarEstado(new CompraCancelada());
    }
}
