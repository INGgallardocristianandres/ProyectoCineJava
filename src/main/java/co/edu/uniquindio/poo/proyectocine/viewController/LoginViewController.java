package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends BaseViewController {
    @FXML private TextField txtCorreoLogin, txtNombre, txtCorreoReg, txtTelefono;
    @FXML private PasswordField txtPassLogin, txtPassReg;

    @FXML private void ingresar() { ejecutar(() -> {
        Usuario u = controller.iniciarSesion(txtCorreoLogin.getText(), txtPassLogin.getText());
        informar("Bienvenido(a), " + u.getNombre());
        controller.alIniciarSesionExitoso();
    }); }

    @FXML private void registrarCliente() { ejecutar(() -> {
        controller.registrarCliente(txtNombre.getText(), txtCorreoReg.getText(),
                txtPassReg.getText(), txtTelefono.getText());
        controller.iniciarSesion(txtCorreoReg.getText(), txtPassReg.getText());
        informar("Cuenta creada e inicio de sesión exitoso");
        controller.alIniciarSesionExitoso();
    }); }
}