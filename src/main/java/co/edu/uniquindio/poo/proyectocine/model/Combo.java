package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Agrupa productos de confitería y aplica un descuento porcentual. */
public class Combo {
    private final String nombre;
    private final List<IProducto> elementos = new ArrayList<>();
    private double descuento;

    /** @param nombre nombre del combo @param descuento descuento porcentual */
    public Combo(String nombre, double descuento) { this.nombre = nombre; this.descuento = descuento; }
    /** @param producto producto que se agregará */
    public void agregarElemento(IProducto producto) { if (producto != null) elementos.add(producto); }
    /** @param producto producto que se removerá */
    public void removerElemento(IProducto producto) { elementos.remove(producto); }
    public String getNombre() { return nombre; }
    public List<IProducto> getElementos() { return Collections.unmodifiableList(elementos); }
    public double getDescuento() { return descuento; }
}
