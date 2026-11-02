package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("C1", "David", "david@mail.com", "pass", "3123456789");
        CuentaPuntos cuentaPuntos = new CuentaPuntos(500);
        cliente.setPuntos(cuentaPuntos);
    }

    @Test
    void testConsultarYRedimirPuntosExitoso() {
        assertEquals(500, cliente.consultarPuntos(), "Debe consultar la cantidad correcta de puntos");

        String resultado = cliente.redimirPuntos(200, "Entrada Gratis 2D");
        assertTrue(resultado.contains("Beneficio obtenido"), "Debe otorgar el beneficio al tener puntos suficientes");
        assertEquals(300, cliente.consultarPuntos(), "El saldo de puntos debe ser descontado");
    }

    @Test
    void testRedimirPuntosInsuficientes() {
        String resultado = cliente.redimirPuntos(1000, "Palomitas Gratis");
        assertTrue(resultado.contains("No cuenta con suficientes puntos"), "Debe denegar el beneficio");
        assertEquals(500, cliente.consultarPuntos(), "Los puntos deben permanecer intactos");
    }

    @Test
    void testRealizarCompraEnHistorial() {
        Compra compra = new Compra("CMP-100", LocalDateTime.now(), 30000.0, cliente);
        cliente.realizarCompra(compra);

        assertEquals(1, cliente.getHistorialcompras().size(), "Debe agregarse la compra al historial del cliente");
        assertEquals(compra, cliente.getHistorialcompras().get(0), "La compra recuperada debe coincidir");
    }
}