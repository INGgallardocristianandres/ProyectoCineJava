package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Patrón Composite: agrupa productos individuales
 * bajo lainterfaz IProducto y aplica un descuento porcentual.
 */
public class Combo implements IProducto {
    private final String nombre;
    private final List<IProducto> elementos = new ArrayList<>();
    private double descuento;

    /**
     * @param nombre nombre del combo
     * @param descuento descuento porcentual (entre 0 y 100)
     */
    public Combo(String nombre, double descuento) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del combo es obligatorio");
        }
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
        this.nombre = nombre.trim();
        this.descuento = descuento;
    }

    /** @param producto producto o sub-combo que se agregará */
    public void agregarElemento(IProducto producto) {
        if (producto != null) {
            elementos.add(producto);
        }
    }

    /** @param producto producto que se removerá */
    public void removerElemento(IProducto producto) {
        elementos.remove(producto);
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Calcula el precio total sumando recursivamente los elementos del combo
     * y aplicando el descuento porcentual configurado.
     */
    @Override
    public double getPrecio() {
        double subtotal = 0.0;
        for (IProducto elemento : elementos) {
            subtotal += elemento.getPrecio();
        }
        return subtotal * (1.0 - (descuento / 100.0));
    }

    public List<IProducto> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
        this.descuento = descuento;
    }
}