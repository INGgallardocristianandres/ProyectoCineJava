package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class PuntosViewController extends BaseViewController {
    @FXML private ComboBox<String> cbBeneficio;
    @FXML private ComboBox<Funcion> cbFuncion;
    @FXML private ComboBox<Asiento> cbAsiento;
    @FXML private ComboBox<IProducto> cbProducto;
    @FXML private TextArea taPuntos;

    @FXML private void initialize() {
        cbBeneficio.getItems().setAll("ENTRADA", "COMBO", "PRODUCTO");
        cbBeneficio.setValue("ENTRADA");
        configurar(cbFuncion, controller.listarFunciones(), f -> f.getPelicula().getTitulo() + " | " + f.getFechaHora().withNano(0));
        configurar(cbAsiento, List.of(), a -> a.getFila() + "-" + a.getNumero() + " (" + a.getEstado() + ")");
        configurar(cbProducto, controller.listarProductos(), p -> p.getNombre() + " ($" + p.getPrecio() + ")");
        consultar();
    }

    @FXML private void cargarAsientos() {
        var disponibles = controller.listarAsientos(cbFuncion.getValue()).stream()
                .filter(Asiento::estaDisponible).toList();
        configurar(cbAsiento, disponibles, a -> a.getFila() + "-" + a.getNumero());
    }

    @FXML private void redimir() { ejecutar(() -> {
        String msg = controller.redimirPuntos(controller.getClienteActual(), cbBeneficio.getValue(),
                cbFuncion.getValue(), cbAsiento.getValue(), cbProducto.getValue());
        consultar();
        cargarAsientos();
        informar(msg);
    }); }

    @FXML private void consultar() {
        Cliente cliente = controller.getClienteActual();
        if (cliente == null) { taPuntos.setText("Inicia sesión como cliente para ver tus puntos"); return; }
        StringBuilder texto = new StringBuilder("Puntos vigentes disponibles: ").append(cliente.consultarPuntos()).append("\n\n");
        if (cliente.getPuntos() != null) {
            for (RegistroPuntos r : cliente.getPuntos().getRegistroPuntos())
                texto.append("Lote: ").append(r.getPuntos()).append(" pts | Obtenidos: ").append(r.getFechaObtencion())
                        .append(" | Vencen: ").append(r.getFechaVencimiento()).append('\n');
        }
        taPuntos.setText(texto.toString());
    }
}