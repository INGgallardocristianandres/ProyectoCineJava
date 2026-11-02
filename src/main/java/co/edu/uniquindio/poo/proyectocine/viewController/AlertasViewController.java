package co.edu.uniquindio.poo.proyectocine.viewController;
import javafx.scene.control.*;
public final class AlertasViewController {
    private AlertasViewController() { }
    public static void informar(String texto) {
        new Alert(Alert.AlertType.INFORMATION, texto, ButtonType.OK).showAndWait();
    }
    public static void ejecutar(Runnable tarea) {
        try { tarea.run(); }
        catch (RuntimeException e) {
            String mensaje = e instanceof NumberFormatException
                ? "Revisa los números; no uses separadores de miles"
                : e instanceof java.time.format.DateTimeParseException
                ? "Usa una fecha válida con formato dd/MM/yyyy HH:mm" : e.getMessage();
            new Alert(Alert.AlertType.ERROR, mensaje == null ? "No se pudo completar la operación" : mensaje,
                    ButtonType.OK).showAndWait();
        }
    }
}
