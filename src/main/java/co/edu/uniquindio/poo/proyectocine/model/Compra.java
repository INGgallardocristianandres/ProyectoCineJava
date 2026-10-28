package co.edu.uniquindio.poo.proyectocine.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una transacción o compra realizada por un cliente en CinemaUQ.
 * Agrupa entradas de cine, productos de confitería y gestiona su estado actual.
 *
 */
public class Compra {

    private String id;
    private LocalDateTime fechaHora;
    private double valorTotal;
    private Cliente cliente;
    private List<Entrada> entradas;
    private List<IProducto> productos;
    private IEstadoCompra estadoCompra;

    /**
     * Constructor para inicializar una nueva compra.
     *
     * @ Identificador único de la compra.
     * @param fechaHora Fecha y hora en que se realiza la transacción.
     * @param valorTotal Costo inicial de la compra.
     * @param cliente Cliente que efectúa la compra.
     */
    public Compra(String id, LocalDateTime fechaHora, double valorTotal, Cliente cliente) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.entradas = new ArrayList<>();
        this.productos = new ArrayList<>();
    }

    /**
     * Calcula el valor total de la compra sumando las entradas y los productos
     * de confitería.
     *
     * @return El valor total recalculado.
     */
    public double calcularTotal() {
        double total = 0.0;

        for (Entrada entrada : entradas) {
            total += entrada.getPrecio();
        }

        for (IProducto producto : productos) {
            total += producto.getPrecio();
        }

        this.valorTotal = total;
        return this.valorTotal;
    }

    /**
     * Cambia el estado actual de la compra.
     *
     * @param nuevoEstado El nuevo estado que asumirá la compra.
     */
    public void cambiarEstado(IEstadoCompra nuevoEstado) {
        this.estadoCompra = nuevoEstado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public List<IProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<IProducto> productos) {
        this.productos = productos;
    }

    public IEstadoCompra getEstadoCompra() {
        return estadoCompra;
    }
}