package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CinemaUQTest {

    @Test
    void testSingletonInstance() {
        CinemaUQ cine1 = CinemaUQ.getInstancia();
        CinemaUQ cine2 = CinemaUQ.getInstancia();
        assertNotNull(cine1, "La instancia del cine no debe ser nula");
        assertSame(cine1, cine2, "CinemaUQ debe retornar siempre exactamente la misma instancia (Singleton)");
    }

    @Test
    void testGestionCarteleraYClientes() {
        CinemaUQ cine = CinemaUQ.getInstancia();
        Pelicula pelicula = new Pelicula("P1", "Interestelar", GeneroPelicula.CIENCIA_FICCION, 169, ClasificacionPelicula.MAYORES_12);
        Sala sala = new Sala(1, 10);
        Funcion funcion = new Funcion("F1", LocalDateTime.now().plusDays(1), 15000.0, pelicula, sala, true);

        cine.agregarFuncion(funcion);
        assertTrue(cine.getCartelera().contains(funcion), "La función debería estar agregada en la cartelera");

        Cliente cliente = new Cliente("C1", "Ana Gómez", "ana@mail.com", "pass123", "3001234567");
        cine.registrarCliente(cliente);
        assertTrue(cine.getClientes().contains(cliente), "El cliente debería estar registrado en el sistema");
    }
}