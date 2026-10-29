package co.edu.uniquindio.poo.proyectocine.model;

public interface IPasarelaPago {
    /**
     * Efectúa un pago usando la pasarela configurada.
     *
     * @param valor valor total de la transacción
     * @param numeroCuenta cuenta que será utilizada para el pago
     * @return {@code true} si la transacción fue aceptada
     */
    boolean efectuarTransaccionExterna(double valor, String numeroCuenta);
}
