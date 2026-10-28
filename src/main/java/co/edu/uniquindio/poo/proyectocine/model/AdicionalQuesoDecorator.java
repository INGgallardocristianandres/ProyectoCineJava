/**
 * Decorador concreto que añade un ingrediente adicional de queso
 * a cualquier producto de confitería base en el sistema CinemaUQ.
 *
 */
package co.edu.uniquindio.poo.proyectocine.model;

public class AdicionalQuesoDecorator extends ProductoDecorator {
    /**
     * Constructor que inicializa el decorador con el producto envuelto.
     *
     * @param producto Producto base al cual se le añadirá el queso.
     */
    public AdicionalQuesoDecorator(IProducto producto) {
        super(producto);
    }
    /**
     * Retorna el precio del producto sumándole el costo fijo del adicional de queso.
     *
     * @return Precio actualizado con el adicional.
     */
    @Override
    public double getPrecio() {
        return super.getPrecio() + 2500.0;
    }
    /**
     * Retorna el nombre del producto concatenando la descripción del adicional.
     *
     * @return Nombre modificado con la especificación del queso.
     */
    @Override
    public String getNombre() {
        return super.getNombre() + " (Con Adicional de Queso)";
    }
}