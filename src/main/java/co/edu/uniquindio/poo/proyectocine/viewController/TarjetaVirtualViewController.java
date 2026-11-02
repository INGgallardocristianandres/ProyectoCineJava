package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class TarjetaVirtualViewController extends BaseViewController {
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private TextField txtMonto;
    @FXML private TextArea taCuenta;
    @FXML private void initialize() { clientes(cbCliente); }
    @FXML private void consultar() { taCuenta.setText(controller.consultarCuenta(cbCliente.getValue())); }
    @FXML private void recargar() { ejecutar(() -> {
        controller.recargar(cbCliente.getValue(), decimal(txtMonto));
        txtMonto.clear(); consultar(); informar("Recarga realizada");
    }); }
}
