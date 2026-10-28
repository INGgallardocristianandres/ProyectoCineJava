/**
 * Clase abstracta que actúa como base para los decoradores de productos
 * de confitería. Implementa el patrón Decorator permitiendo añadir
 * funcionalidades de manera dinámica.
 */
package co.edu.uniquindio.poo.proyectocine.model;

public abstract class ProductoDecorator implements IProducto{
    protected IProducto producto;
    /**
     * Constructor que recibe el producto a envolver.
     *
     * @param producto Objeto que implementa IProducto a ser decorado.
     */
    public ProductoDecorator(IProducto producto) {
        this.producto = producto;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public double getPrecio() {
        return this.producto.getPrecio();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getNombre() {
        return this.producto.getNombre();
    }
}
