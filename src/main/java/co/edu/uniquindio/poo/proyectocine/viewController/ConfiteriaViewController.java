package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class ConfiteriaViewController extends BaseViewController {
    @FXML private TextField txtNombre, txtPrecio;
    @FXML private ListView<IProducto> lvProductos;
    @FXML private void initialize() {
        celdas(lvProductos, p -> p.getNombre() + " | " + p.getPrecio());
        lvProductos.getItems().setAll(controller.listarProductos());
    }
    @FXML private void registrar() { ejecutar(() -> {
        controller.registrarProducto(txtNombre.getText(), decimal(txtPrecio));
        lvProductos.getItems().setAll(controller.listarProductos());
        txtNombre.clear(); txtPrecio.clear();
        informar("Producto registrado");
    }); }
}
