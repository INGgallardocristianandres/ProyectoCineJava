package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.controller.MainController;
import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.scene.control.*;
import java.util.*;

import javafx.util.StringConverter;
import java.util.function.Function;
public abstract class BaseViewController {
    protected final MainController controller = MainController.getInstancia();
    protected void ejecutar(Runnable tarea) { AlertasViewController.ejecutar(tarea); }
    protected void informar(String texto) { AlertasViewController.informar(texto); }
    protected int entero(TextField campo) { return Integer.parseInt(campo.getText().trim()); }
    protected double decimal(TextField campo) {
        return Double.parseDouble(campo.getText().trim().replace(',', '.'));
    }
    protected <T> void configurar(ComboBox<T> combo, List<T> datos, Function<T,String> etiqueta) {
        combo.setConverter(new StringConverter<>() {
            public String toString(T valor) { return valor == null ? "" : etiqueta.apply(valor); }
            public T fromString(String texto) { return null; }
        });
        combo.getItems().setAll(datos);
    }
    protected void clientes(ComboBox<Cliente> combo) {
        configurar(combo, controller.listarClientes(), c -> c.getNombre() + " - " + c.getCorreo());
    }
    protected <T> void celdas(ListView<T> lista, Function<T,String> etiqueta) {
        lista.setCellFactory(v -> new ListCell<>() {
            protected void updateItem(T item, boolean vacia) {
                super.updateItem(item, vacia);
                setText(vacia || item == null ? null : etiqueta.apply(item));
            }
        });
    }
}
