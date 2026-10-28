package co.edu.uniquindio.poo.proyectocine.model;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Simula el servicio de pagos de un proveedor externo. */
public class PasarelaExterna {
    private final String apiKey;
    private final String url;
    private boolean modoSandbox;
    private final Map<String, String> transacciones = new ConcurrentHashMap<>();

    /**
     * Crea una pasarela externa en modo sandbox.
     *
     * @param apiKey clave de autenticación del proveedor
     * @param url dirección del servicio externo
     */
    public PasarelaExterna(String apiKey, String url) {
        this.apiKey = apiKey;
        this.url = url;
        this.modoSandbox = true;
    }

    /**
     * Registra una transacción de pago simulada.
     *
     * @param valor valor que se cobrará
     * @param numeroCuenta cuenta asociada al cobro
     * @return {@code true} si los datos son válidos y el cobro fue aprobado
     */
    public boolean efectuarTransaccion(double valor, String numeroCuenta) {
        if (valor <= 0 || numeroCuenta == null || numeroCuenta.isBlank()) {
            return false;
        }
        String id = UUID.randomUUID().toString();
        transacciones.put(id, "APROBADA");
        return true;
    }

    /**
     * Consulta el estado de una transacción previamente registrada.
     *
     * @param idTransaccion identificador de la transacción
     * @return estado de la transacción o {@code NO_ENCONTRADA}
     */
    public String verificarEstadoTransaccion(String idTransaccion) {
        return transacciones.getOrDefault(idTransaccion, "NO_ENCONTRADA");
    }

    /** @return clave de autenticación configurada */
    public String getApiKey() { return apiKey; }
    /** @return dirección del servicio externo */
    public String getUrl() { return url; }
    /** @return {@code true} si la pasarela opera en modo sandbox */
    public boolean isModoSandbox() { return modoSandbox; }
    /** @param modoSandbox indica si se debe activar el modo sandbox */
    public void setModoSandbox(boolean modoSandbox) { this.modoSandbox = modoSandbox; }
}
