package co.edu.uniquindio.poo.proyectocine.model;

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
}
