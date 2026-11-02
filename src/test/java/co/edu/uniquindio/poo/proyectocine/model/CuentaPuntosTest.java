package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CuentaPuntosTest {

    @Test
    void testAcumularYGastarPuntos() {
        CuentaPuntos cuenta = new CuentaPuntos(100);
        assertEquals(100, cuenta.getCantidadPuntos(), "El saldo inicial debe ser 100");

        cuenta.acumularPuntos(50);
        assertEquals(150, cuenta.getCantidadPuntos(), "El saldo total debe actualizarse a 150 tras acumular");

        boolean gastadoExitoso = cuenta.gastarPuntos(60);
        assertTrue(gastadoExitoso, "El gasto de puntos debería ser exitoso");
        assertEquals(90, cuenta.getCantidadPuntos(), "El saldo restante debe ser 90");
    }

    @Test
    void testGastarPuntosInsuficientes() {
        CuentaPuntos cuenta = new CuentaPuntos(50);
        boolean gastadoExitoso = cuenta.gastarPuntos(100);
        assertFalse(gastadoExitoso, "No debería permitir gastar más puntos de los disponibles");
        assertEquals(50, cuenta.getCantidadPuntos(), "El saldo debe permanecer intacto");
    }
}