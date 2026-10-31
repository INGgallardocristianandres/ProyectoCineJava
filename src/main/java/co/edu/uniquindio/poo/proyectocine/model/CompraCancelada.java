package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Estado terminal de una compra cancelada.
 */
public class CompraCancelada implements IEstadoCompra {
    @Override
    public void procesarPago(Compra compra) {
        throw new IllegalStateException("Una compra cancelada no puede pagarse");
    }

    @Override
    public void cancelar(Compra compra) {
        throw new IllegalStateException("La compra ya fue cancelada");
    }
}
