package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class ReporteViewController extends BaseViewController {
    @FXML private ComboBox<String> cbFormato;
    @FXML private TextArea taReporte;
    @FXML private void initialize() { cbFormato.getItems().setAll("TXT", "PDF"); cbFormato.setValue("TXT"); }
    @FXML private void generar() { ejecutar(() -> {
        taReporte.setText(controller.generarReporte(cbFormato.getValue()));
        informar("Reporte procesado por el generador " + cbFormato.getValue());
    }); }
}
