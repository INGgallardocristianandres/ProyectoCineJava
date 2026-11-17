package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Builder: permite construir objetos Combo complejos paso a paso
 * antes de entregar la instancia final validada.
 */
public class ComboBuilder {
    private String nombre;
    private double descuento;
    private final List<IProducto> elementos = new ArrayList<>();

    public ComboBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ComboBuilder conDescuento(double descuento) {
        this.descuento = descuento;
        return this;
    }

    public ComboBuilder agregarProducto(IProducto producto) {
        if (producto != null) {
            this.elementos.add(producto);
        }
        return this;
    }

    public ComboBuilder agregarProductos(List<IProducto> productos) {
        if (productos != null) {
            for (IProducto producto : productos) {
                agregarProducto(producto);
            }
        }
        return this;
    }

    public Combo build() {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("No se puede construir un combo sin nombre");
        }
        if (elementos.isEmpty()) {
            throw new IllegalStateException("El combo debe tener al menos un producto");
        }
        Combo combo = new Combo(nombre, descuento);
        for (IProducto producto : elementos) {
            combo.agregarElemento(producto);
        }
        return combo;
    }
}