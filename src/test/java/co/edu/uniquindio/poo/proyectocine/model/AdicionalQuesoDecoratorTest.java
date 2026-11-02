package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdicionalQuesoDecoratorTest {

    @Test
    void testDecoradorDeQueso() {
        IProducto productoBase = new Producto("Palomitas Medianas", 10000.0);
        IProducto productoConQueso = new AdicionalQuesoDecorator(productoBase);

        assertEquals("Palomitas Medianas (Con Adicional de Queso)", productoConQueso.getNombre(), "El nombre debe incluir el adicional");
        assertEquals(12500.0, productoConQueso.getPrecio(), 0.001, "El precio debe sumar los 2500.0 del adicional de queso");
    }
}