package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class ClienteViewController extends BaseViewController {
    @FXML private TextField txtNombre, txtCorreo, txtTelefono;
    @FXML private PasswordField txtContrasenia;
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private TextArea taCuenta;
    @FXML private void initialize() { clientes(cbCliente); }
    @FXML private void registrar() { ejecutar(() -> {
        Cliente c = controller.registrarCliente(txtNombre.getText(), txtCorreo.getText(),
                txtContrasenia.getText(), txtTelefono.getText());
        clientes(cbCliente); cbCliente.setValue(c); consultar();
        txtNombre.clear(); txtCorreo.clear(); txtContrasenia.clear(); txtTelefono.clear();
        informar("Cliente registrado");
    }); }
    @FXML private void consultar() { taCuenta.setText(controller.consultarCuenta(cbCliente.getValue())); }
}
