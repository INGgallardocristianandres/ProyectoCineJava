package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class CompraViewController extends BaseViewController {
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<Funcion> cbFuncion;
    @FXML private ComboBox<IProducto> cbProducto;
    @FXML private ListView<Asiento> lvAsientos;
    @FXML private ListView<IProducto> lvCarrito;
    @FXML private Label lblTotal;

    @FXML private void initialize() {
        Cliente actual = controller.getClienteActual();
        if (actual != null) {
            configurar(cbCliente, List.of(actual), c -> c.getNombre() + " - Saldo: $" + c.getTarjetaVirtual().consultarSaldo());
            cbCliente.setValue(actual);
        } else {
            clientes(cbCliente);
        }
        configurar(cbFuncion, controller.listarFunciones(), f -> f.getPelicula().getTitulo()
                + " | " + f.getFechaHora().withNano(0) + " | Sala " + f.getSala().getNumeroSala()
                + " ($" + f.getPrecioEntrada() + ")");
        configurar(cbProducto, controller.listarProductos(), p -> p.getNombre() + " | $" + String.format("%.2f", p.getPrecio()));
        celdas(lvAsientos, a -> a.getFila() + "-" + a.getNumero() + " | " + a.getEstado());
        celdas(lvCarrito, p -> p.getNombre() + " | $" + String.format("%.2f", p.getPrecio()));
        lvAsientos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAsientos.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Asiento>) c -> total());
        lvCarrito.getItems().addListener((ListChangeListener<IProducto>) c -> total());
        total();
    }

    @FXML private void cargarAsientos() {
        lvAsientos.getItems().setAll(controller.listarAsientos(cbFuncion.getValue()));
        lvAsientos.getSelectionModel().clearSelection();
        total();
    }

    @FXML private void agregarProducto() { ejecutar(() -> {
        IProducto p = Objects.requireNonNull(cbProducto.getValue(), "Selecciona un producto o combo");
        lvCarrito.getItems().add(p);
    }); }

    @FXML private void agregarProductoConQueso() { ejecutar(() -> {
        IProducto base = Objects.requireNonNull(cbProducto.getValue(), "Selecciona un producto o combo");
        lvCarrito.getItems().add(new AdicionalQuesoDecorator(base));
    }); }

    @FXML private void quitarProducto() {
        int i = lvCarrito.getSelectionModel().getSelectedIndex();
        if (i >= 0) lvCarrito.getItems().remove(i);
    }

    @FXML private void comprar() { ejecutar(() -> {
        Cliente c = cbCliente.getValue();
        Compra compra = controller.comprar(c, cbFuncion.getValue(),
                List.copyOf(lvAsientos.getSelectionModel().getSelectedItems()), List.copyOf(lvCarrito.getItems()));
        lvCarrito.getItems().clear();
        cargarAsientos();
        configurar(cbCliente, List.of(c), cl -> cl.getNombre() + " - Saldo: $" + cl.getTarjetaVirtual().consultarSaldo());
        cbCliente.setValue(c);
        informar("Compra registrada: " + compra.getId() + "\nTotal pagado: $" + String.format("%.2f", compra.getValorTotal()));
    }); }

    private void total() {
        Funcion f = cbFuncion.getValue();
        double total = f == null ? 0 : f.getPrecioEntrada() * lvAsientos.getSelectionModel().getSelectedItems().size();
        for (IProducto p : lvCarrito.getItems()) total += p.getPrecio();
        lblTotal.setText(String.format("Total: $%.2f", total));
    }
}