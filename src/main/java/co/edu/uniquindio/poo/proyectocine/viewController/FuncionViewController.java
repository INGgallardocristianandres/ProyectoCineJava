package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.*;
public class FuncionViewController extends BaseViewController {
    @FXML private ComboBox<Pelicula> cbPelicula;
    @FXML private ComboBox<Sala> cbSala;
    @FXML private TextField txtPrecio;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<Integer> cbHora, cbMinuto;
    @FXML private ListView<Funcion> lvFunciones;
    private final DateTimeFormatter formato = DateTimeFormatter
            .ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
    @FXML private void initialize() {
        configurar(cbHora, java.util.stream.IntStream.range(0, 24).boxed().toList(),
                hora -> String.format("%02d", hora));
        configurar(cbMinuto, java.util.stream.IntStream.range(0, 60).boxed().toList(),
                minuto -> String.format("%02d", minuto));
        dpFecha.setEditable(false);
        dpFecha.setDayCellFactory(calendario -> new DateCell() {
            @Override public void updateItem(LocalDate fecha, boolean vacia) {
                super.updateItem(fecha, vacia);
                setDisable(vacia || fecha == null || fecha.isBefore(LocalDate.now()));
            }
        });
        configurar(cbPelicula, controller.listarPeliculas(), Pelicula::getTitulo);
        configurar(cbSala, controller.listarSalas(), s -> "Sala " + s.getNumeroSala());
        celdas(lvFunciones, f -> f.getPelicula().getTitulo() + " | " + f.getFechaHora().format(formato)
                + " | Sala " + f.getSala().getNumeroSala() + " | " + f.getPrecioEntrada());
        lvFunciones.getItems().setAll(controller.listarFunciones());
    }
    @FXML private void programar() { ejecutar(() -> {
        controller.programarFuncion(cbPelicula.getValue(), cbSala.getValue(),
                LocalDateTime.of(
                        Objects.requireNonNull(dpFecha.getValue(), "Selecciona una fecha"),
                        LocalTime.of(
                                Objects.requireNonNull(cbHora.getValue(), "Selecciona la hora"),
                                Objects.requireNonNull(cbMinuto.getValue(), "Selecciona los minutos"))), decimal(txtPrecio));
        lvFunciones.getItems().setAll(controller.listarFunciones());
        informar("Función programada");
    }); }
}
