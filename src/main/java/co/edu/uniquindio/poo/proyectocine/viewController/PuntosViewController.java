package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

public class PuntosViewController extends BaseViewController {
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private TextArea taPuntos;
    @FXML private void initialize() { clientes(cbCliente); }
    @FXML private void consultar() {
        Cliente cliente = cbCliente.getValue();
        if (cliente == null) { taPuntos.setText("Selecciona un cliente"); return; }
        StringBuilder texto = new StringBuilder("Puntos: ").append(cliente.consultarPuntos()).append('\n');
        if (cliente.getPuntos() != null) {
            for (RegistroPuntos r : cliente.getPuntos().getRegistroPuntos())
                texto.append(r.getPuntos()).append(" | Obtenidos: ").append(r.getFechaObtencion())
                     .append(" | Vencen: ").append(r.getFechaVencimiento()).append('\n');
        }
        taPuntos.setText(texto.toString());
    }
}
