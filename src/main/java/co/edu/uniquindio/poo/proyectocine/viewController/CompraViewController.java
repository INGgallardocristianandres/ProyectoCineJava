package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

import javafx.collections.ListChangeListener;
public class CompraViewController extends BaseViewController {
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<Funcion> cbFuncion;
    @FXML private ComboBox<IProducto> cbProducto;
    @FXML private ListView<Asiento> lvAsientos;
    @FXML private ListView<IProducto> lvCarrito;
    @FXML private Label lblTotal;
    @FXML private void initialize() {
        clientes(cbCliente);
        configurar(cbFuncion, controller.listarFunciones(), f -> f.getPelicula().getTitulo()
                + " | " + f.getFechaHora() + " | Sala " + f.getSala().getNumeroSala());
        configurar(cbProducto, controller.listarProductos(), p -> p.getNombre() + " | " + p.getPrecio());
        celdas(lvAsientos, a -> a.getFila() + "-" + a.getNumero() + " | " + a.getEstado());
        celdas(lvCarrito, p -> p.getNombre() + " | " + p.getPrecio());
        lvAsientos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAsientos.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Asiento>) c -> total());
        lvCarrito.getItems().addListener((ListChangeListener<IProducto>) c -> total());
        total();
    }
    @FXML private void cargarAsientos() {
        lvAsientos.getItems().setAll(controller.listarAsientos(cbFuncion.getValue()));
        lvAsientos.getSelectionModel().clearSelection(); total();
    }
    @FXML private void agregarProducto() { ejecutar(() -> {
        IProducto p = Objects.requireNonNull(cbProducto.getValue(), "Selecciona un producto");
        lvCarrito.getItems().add(p);
    }); }
    @FXML private void quitarProducto() {
        int i = lvCarrito.getSelectionModel().getSelectedIndex();
        if (i >= 0) lvCarrito.getItems().remove(i);
    }
    @FXML private void comprar() { ejecutar(() -> {
        Compra compra = controller.comprar(cbCliente.getValue(), cbFuncion.getValue(),
                List.copyOf(lvAsientos.getSelectionModel().getSelectedItems()), List.copyOf(lvCarrito.getItems()));
        lvCarrito.getItems().clear(); cargarAsientos();
        informar("Compra registrada: " + compra.getId() + "\nTotal: " + compra.getValorTotal());
    }); }
    private void total() {
        Funcion f = cbFuncion.getValue();
        double total = f == null ? 0 : f.getPrecioEntrada()
                * lvAsientos.getSelectionModel().getSelectedItems().size();
        for (IProducto p : lvCarrito.getItems()) total += p.getPrecio();
        lblTotal.setText(String.format("Total: %.2f", total));
    }
}
