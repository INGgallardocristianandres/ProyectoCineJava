package co.edu.uniquindio.poo.proyectocine.model;

/** Producto sencillo que puede formar parte de un combo. */
public class Producto implements IProducto {
    private final String nombre;
    private final double precio;

    /** @param nombre nombre del producto @param precio precio del producto */
    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
    @Override public String getNombre() { return nombre; }
    @Override public double getPrecio() { return precio; }
}
