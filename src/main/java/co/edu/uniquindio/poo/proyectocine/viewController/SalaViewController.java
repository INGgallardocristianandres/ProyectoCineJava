package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class SalaViewController extends BaseViewController {
    @FXML private TextField txtNumero, txtCapacidad;
    @FXML private ListView<Sala> lvSalas;
    @FXML private void initialize() {
        celdas(lvSalas, s -> "Sala " + s.getNumeroSala() + " | Capacidad: " + s.getCapacidad());
        lvSalas.getItems().setAll(controller.listarSalas());
    }
    @FXML private void registrar() { ejecutar(() -> {
        controller.registrarSala(entero(txtNumero), entero(txtCapacidad));
        lvSalas.getItems().setAll(controller.listarSalas());
        txtNumero.clear(); txtCapacidad.clear();
        informar("Sala registrada");
    }); }
}
