package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class ConfiteriaViewController extends BaseViewController {
    @FXML private TextField txtNombre, txtPrecio, txtNombreCombo, txtDescuentoCombo;
    @FXML private ListView<IProducto> lvProductos, lvSeleccionCombo;

    @FXML private void initialize() {
        celdas(lvProductos, p -> p.getNombre() + " | $" + String.format("%.2f", p.getPrecio()));
        celdas(lvSeleccionCombo, p -> p.getNombre() + " | $" + String.format("%.2f", p.getPrecio()));
        lvSeleccionCombo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        refrescar();
    }

    private void refrescar() {
        lvProductos.getItems().setAll(controller.listarProductos());
        lvSeleccionCombo.getItems().setAll(controller.listarProductos());
    }

    @FXML private void registrar() { ejecutar(() -> {
        controller.registrarProducto(txtNombre.getText(), decimal(txtPrecio));
        refrescar();
        txtNombre.clear(); txtPrecio.clear();
        informar("Producto registrado");
    }); }

    @FXML private void crearCombo() { ejecutar(() -> {
        List<IProducto> elegidos = List.copyOf(lvSeleccionCombo.getSelectionModel().getSelectedItems());
        Combo combo = controller.registrarCombo(txtNombreCombo.getText(), decimal(txtDescuentoCombo), elegidos);
        refrescar();
        txtNombreCombo.clear(); txtDescuentoCombo.clear();
        informar("Combo creado con Builder: " + combo.getNombre() + " ($" + String.format("%.2f", combo.getPrecio()) + ")");
    }); }
}