package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Estado inicial de una compra.
 */
public class CompraPendiente implements IEstadoCompra {
    @Override
    public void procesarPago(Compra compra) {
        if (compra == null) throw new IllegalArgumentException("La compra es obligatoria");
        compra.cambiarEstado(new CompraPagada());
    }

    @Override
    public void cancelar(Compra compra) {
        if (compra == null) throw new IllegalArgumentException("La compra es obligatoria");
        compra.cambiarEstado(new CompraCancelada());
    }
}
