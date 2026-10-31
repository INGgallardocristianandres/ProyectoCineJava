package co.edu.uniquindio.poo.proyectocine.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *  Fachada para que el resto de la aplicación no dependa de la API externa.
 */
public class CompraFacade {
    private final IPasarelaPago pasarelaPago;

    public CompraFacade(IPasarelaPago pasarelaPago) {
        if (pasarelaPago == null) {
            throw new IllegalArgumentException("La pasarela de pago es obligatoria");
        }
        this.pasarelaPago = pasarelaPago;
    }

    public boolean realizarCobro(double monto, String numeroCuenta) {
        return pasarelaPago.efectuarTransaccionExterna(monto, numeroCuenta);
    }

    public boolean procesarCompra(Cliente cliente, Funcion funcion,
                                  List<Asiento> asientos, List<IProducto> productos) {
        if (cliente == null || funcion == null || !funcion.isActivo()
                || asientos == null || asientos.isEmpty() || productos == null) return false;
        if (asientos.stream().anyMatch(asiento -> asiento == null || !asiento.estaDisponible())) return false;

        double total = asientos.size() * funcion.getPrecioEntrada();
        for (IProducto producto : productos) if (producto != null) total += producto.getPrecio();
        TarjetaVirtual tarjeta = cliente.getTarjetaVirtual();
        if (tarjeta == null || tarjeta.getEstado() != EstadoTarjeta.ACTIVA || !tarjeta.debitar(total)) return false;

        Compra compra = new Compra(UUID.randomUUID().toString(), LocalDateTime.now(), total, cliente);
        for (Asiento asiento : asientos) {
            asiento.cambiarEstado(EstadoAsiento.OCUPADO);
            compra.agregarEntrada(new Entrada(UUID.randomUUID().toString(), funcion.getPrecioEntrada(), funcion, asiento));
        }
        for (IProducto producto : productos) compra.agregarProducto(producto);
        compra.procesarPago();
        cliente.realizarCompra(compra);
        return true;
    }
}
