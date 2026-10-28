/**
 * Interfaz que define el comportamiento común para todos los productos
 * del sistema de confitería de CinemaUQ (tanto productos base como decorados).
 *
 **/
package co.edu.uniquindio.poo.proyectocine.model;

public interface IProducto {
    /**
     * Obtiene el precio total del producto.
     * @return El costo del producto en formato double.
     */
        double getPrecio();
    /**
     * Obtiene la descripción o nombre del producto.
     * @return Una cadena con el nombre del producto.
     */
        String getNombre();
    }

