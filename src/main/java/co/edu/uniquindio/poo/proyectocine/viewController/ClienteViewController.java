package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClienteViewController extends BaseViewController {
    @FXML private ComboBox<Compra> cbComprasCancelables;
    @FXML private TextArea taCuenta;

    @FXML private void initialize() {
        refrescar();
    }

    private void refrescar() {
        Cliente actual = controller.getClienteActual();
        if (actual == null) return;
        taCuenta.setText(controller.consultarCuenta(actual));
        var pagadas = actual.getHistorialcompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraPagada && !c.getEntradas().isEmpty())
                .toList();
        configurar(cbComprasCancelables, pagadas, c -> c.getEntradas().get(0).getFuncion().getPelicula().getTitulo()
                + " | Total: $" + String.format("%.2f", c.getValorTotal())
                + " | Función: " + c.getEntradas().get(0).getFuncion().getFechaHora().withNano(0));
    }

    @FXML private void cancelarCompra() { ejecutar(() -> {
        double reembolso = controller.cancelarCompra(cbComprasCancelables.getValue());
        refrescar();
        informar("Compra cancelada. Reembolso abonado a tu tarjeta: $" + String.format("%.2f", reembolso));
    }); }
}