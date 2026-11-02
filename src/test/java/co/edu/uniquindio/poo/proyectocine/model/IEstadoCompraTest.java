package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class IEstadoCompraTest {

    private Compra compra;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("C1", "Laura", "laura@mail.com", "1234", "3000000000");
        compra = new Compra("CMP-1", LocalDateTime.now(), 25000.0, cliente);
    }

    @Test
    void testEstadoInicialPendienteAPagado() {
        assertTrue(compra.getEstadoCompra() instanceof CompraPendiente, "El estado inicial de la compra debe ser CompraPendiente");

        compra.procesarPago();
        assertTrue(compra.getEstadoCompra() instanceof CompraPagada, "Al procesar pago, la compra debe pasar a CompraPagada");
    }

    @Test
    void testPagarCompraYaPagadaLanzaExcepcion() {
        compra.procesarPago();
        assertThrows(IllegalStateException.class, () -> compra.procesarPago(), "Re-pagar una compra pagada debe lanzar IllegalStateException");
    }

    @Test
    void testCancelarCompraPagada() {
        compra.procesarPago();
        compra.cancelar();
        assertTrue(compra.getEstadoCompra() instanceof CompraCancelada, "Una compra pagada puede cancelarse y pasar a CompraCancelada");
    }

    @Test
    void testOperacionesEnCompraCanceladaLanzanExcepcion() {
        compra.cancelar();
        assertThrows(IllegalStateException.class, () -> compra.procesarPago(), "Pagar una compra cancelada debe fallar");
        assertThrows(IllegalStateException.class, () -> compra.cancelar(), "Volver a cancelar una compra cancelada debe fallar");
    }
}