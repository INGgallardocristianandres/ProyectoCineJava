package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompraFacadeTest {

    private CompraFacade compraFacade;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;

    @BeforeEach
    void setUp() {
        IPasarelaPago pasarelaMock = (monto, cuenta) -> true;
        compraFacade = new CompraFacade(pasarelaMock);
        Sala sala = new Sala(1, 50);

        TarjetaVirtual tarjeta = new TarjetaVirtual("1234", 100000.0, EstadoTarjeta.ACTIVA);
        cliente = new Cliente("1", "Carlos", "carlos@gmail.com", "12345", "3111111111");
        cliente.setTarjetaVirtual(tarjeta);

        Pelicula pelicula = new Pelicula("P1", "Avatar", GeneroPelicula.ACCION, 120, ClasificacionPelicula.GENERAL);
        funcion = new Funcion("F1", LocalDateTime.now(), 20000.0, pelicula, sala, true);
        asiento = new Asiento("A1", 'A', 1, EstadoAsiento.DISPONIBLE);
    }

    @Test
    void testProcesarCompraExitosos() {
        List<Asiento> asientos = new ArrayList<>();
        asientos.add(asiento);
        List<IProducto> productos = new ArrayList<>();

        boolean resultado = compraFacade.procesarCompra(cliente, funcion, asientos, productos);

        assertTrue(resultado, "La compra debería procesarse con éxito");
        assertEquals(EstadoAsiento.OCUPADO, asiento.getEstado(), "El asiento debería quedar ocupado");
        assertEquals(80000.0, cliente.getTarjetaVirtual().getSaldo(), "Se debió descontar el valor de la entrada del saldo");
    }
}