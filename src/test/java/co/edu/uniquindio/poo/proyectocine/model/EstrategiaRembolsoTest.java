package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstrategiaRembolsoTest {

    @Test
    void testRembolsoCompleto() {
        IEstrategiaRembolso estrategia = new RembolsoCompletoStrategy();
        double rembolso = estrategia.calcularRembolso(40000.0, 24.0);
        assertEquals(40000.0, rembolso, 0.001, "Debe reembolsar el 100% del valor pagado");
    }

    @Test
    void testRembolsoParcial() {
        IEstrategiaRembolso estrategia = new RembolsoParcialStrategy();
        double rembolso = estrategia.calcularRembolso(40000.0, 6.0);
        assertEquals(20000.0, rembolso, 0.001, "Debe reembolsar el 50% del valor pagado");
    }

    @Test
    void testSinRembolso() {
        IEstrategiaRembolso estrategia = new SinRembolsoStrategy();
        double rembolso = estrategia.calcularRembolso(40000.0, 1.0);
        assertEquals(0.0, rembolso, 0.001, "El reembolso debe ser 0.0");
    }
}