package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TarjetaVirtualTest {

    private TarjetaVirtual tarjeta;

    @BeforeEach
    void setUp() {
        tarjeta = new TarjetaVirtual("TV-001", 50000.0, EstadoTarjeta.ACTIVA);
    }

    @Test
    void testAbonarExitoso() {
        tarjeta.abonar(20000.0);
        assertEquals(70000.0, tarjeta.consultarSaldo(), 0.001, "El saldo debe incrementarse correctamente");
        assertEquals(1, tarjeta.getMovimientos().size(), "Debe haber 1 movimiento registrado");
        assertEquals(TipoMovimiento.ABONO, tarjeta.getMovimientos().get(0).getTipo(), "El movimiento debe ser de tipo ABONO");
    }

    @Test
    void testAbonarMontoInvalido() {
        tarjeta.abonar(-5000.0);
        assertEquals(50000.0, tarjeta.consultarSaldo(), "El saldo no debe cambiar si el monto es negativo o 0");
        assertTrue(tarjeta.getMovimientos().isEmpty(), "No debe haber movimientos registrados");
    }

    @Test
    void testDebitarExitoso() {
        boolean resultado = tarjeta.debitar(30000.0);
        assertTrue(resultado, "El débito debe ser exitoso cuando hay saldo suficiente");
        assertEquals(20000.0, tarjeta.consultarSaldo(), 0.001, "El saldo debe actualizarse correctamente");
        assertEquals(1, tarjeta.getMovimientos().size(), "Debe haber 1 movimiento de débito registrado");
        assertEquals(TipoMovimiento.DEBITO, tarjeta.getMovimientos().get(0).getTipo(), "El movimiento debe ser de tipo DEBITO");
    }

    @Test
    void testDebitarSaldoInsuficiente() {
        boolean resultado = tarjeta.debitar(80000.0);
        assertFalse(resultado, "El débito debe fallar si el monto supera el saldo disponible");
        assertEquals(50000.0, tarjeta.consultarSaldo(), "El saldo no debe cambiar");
        assertTrue(tarjeta.getMovimientos().isEmpty(), "No debe generarse movimiento");
    }
}