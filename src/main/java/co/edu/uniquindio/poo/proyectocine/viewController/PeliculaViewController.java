package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class PeliculaViewController extends BaseViewController {
    @FXML private TextField txtTitulo, txtDuracion;
    @FXML private ComboBox<GeneroPelicula> cbGenero;
    @FXML private ComboBox<ClasificacionPelicula> cbClasificacion;
    @FXML private ListView<Pelicula> lvPeliculas;
    @FXML private void initialize() {
        cbGenero.getItems().setAll(GeneroPelicula.values());
        cbClasificacion.getItems().setAll(ClasificacionPelicula.values());
        celdas(lvPeliculas, p -> p.getTitulo() + " | " + p.getDuracionMinutos()
                + " min | " + p.getGenero() + " | " + p.getClasificacion());
        lvPeliculas.getItems().setAll(controller.listarPeliculas());
    }
    @FXML private void registrar() { ejecutar(() -> {
        controller.registrarPelicula(txtTitulo.getText(), cbGenero.getValue(),
                cbClasificacion.getValue(), entero(txtDuracion));
        lvPeliculas.getItems().setAll(controller.listarPeliculas());
        txtTitulo.clear(); txtDuracion.clear();
        informar("Película registrada");
    }); }
}
