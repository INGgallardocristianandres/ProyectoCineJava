package co.edu.uniquindio.poo.proyectocine.model;

public class ProductoConfiteria implements IProducto {
    private String id;
    private String nombre;
    private double precio;
    private String categoria;
    private boolean activo;

    /**
     * Crea un producto de confitería.
     *
     * @param id identificador único del producto
     * @param nombre nombre comercial del producto
     * @param precio precio de venta
     * @param categoria categoría del producto
     * @param activo indica si el producto está disponible
     */
    public ProductoConfiteria(String id, String nombre, double precio, String categoria, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.activo = activo;
    }

    /** @return identificador del producto */
    public String getId() { return id; }
    /** @return nombre del producto */
    @Override
    public String getNombre() { return nombre; }
    @Override
    /** @return precio del producto */
    public double getPrecio() { return precio; }
    /** @return categoría del producto */
    public String getCategoria() { return categoria; }
    /** @return {@code true} si el producto está activo */
    public boolean isActivo() { return activo; }
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
