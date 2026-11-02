package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HistorialComandosTest {

    private HistorialComandos historial;
    private Administrador admin;
    private Pelicula pelicula;

    @BeforeEach
    void setUp() {
        historial = new HistorialComandos();
        admin = new Administrador("A1", "Admin Central", "admin@cine.com", "1234", "TOTAL");
        pelicula = new Pelicula("P1", "El Origen", GeneroPelicula.CIENCIA_FICCION, 148, ClasificacionPelicula.MAYORES_12);
    }

    @Test
    void testEjecutarYDeshacerComando() {
        IComando comando = new GestionarPeliculaCommand(admin, pelicula, "crear");


        assertDoesNotThrow(() -> historial.ejecutarComando(comando), "La ejecución del comando debe realizarse sin errores");


        assertDoesNotThrow(() -> historial.deshacer(), "Deshacer la última acción no debe generar errores");
    }

    @Test
    void testDeshacerPilaVacia() {
        assertDoesNotThrow(() -> historial.deshacer(), "Deshacer en una pila vacía debe manejarse de forma segura");
    }
}