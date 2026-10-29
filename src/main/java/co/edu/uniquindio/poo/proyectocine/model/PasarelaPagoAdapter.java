package co.edu.uniquindio.poo.proyectocine.model;


public class PasarelaPagoAdapter implements IPasarelaPago {
    private final PasarelaExterna pasarelaExterna;

    /**
     * Crea un adaptador para la pasarela externa indicada.
     *
     * @param pasarelaExterna servicio externo que será adaptado
     * @throws IllegalArgumentException si la pasarela es {@code null}
     */
    public PasarelaPagoAdapter(PasarelaExterna pasarelaExterna) {
        if (pasarelaExterna == null) {
            throw new IllegalArgumentException("La pasarela externa es obligatoria");
        }
        this.pasarelaExterna = pasarelaExterna;
    }

    /**
     * Traduce la operación de la interfaz interna a la API externa.
     *
     * @param valor valor de la transacción
     * @param numeroCuenta cuenta utilizada para el pago
     * @return {@code true} si el cobro fue aceptado
     */
    @Override
    public boolean efectuarTransaccionExterna(double valor, String numeroCuenta) {
        return pasarelaExterna.efectuarTransaccion(valor, numeroCuenta);
    }

    /** @return pasarela externa adaptada */
    public PasarelaExterna getPasarelaExterna() { return pasarelaExterna; }
}
