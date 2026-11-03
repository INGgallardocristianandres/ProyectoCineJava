package co.edu.uniquindio.poo.proyectocine.viewController;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PuntosViewController extends BaseViewController {
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private TextArea taPuntos;
    @FXML private Label lblSaldo, lblCosto;
    @FXML private ComboBox<String> cbTipo, cbTipoConfig;
    @FXML private ComboBox<Funcion> cbFuncion;
    @FXML private ComboBox<Asiento> cbAsiento;
    @FXML private ComboBox<IProducto> cbProducto;
    @FXML private ComboBox<Object> cbBeneficioConfig;
    @FXML private VBox panelEntrada, panelProducto;
    @FXML private TextField txtCostoConfig;
    @FXML private Button btnCanjear;
    private final DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML private void initialize() {
        clientes(cbCliente);
        configurar(cbFuncion, controller.listarFunciones().stream()
                .filter(f -> f.getFechaHora().isAfter(LocalDateTime.now())).toList(),
                f -> f.getPelicula().getTitulo() + " | " + f.getFechaHora().format(fecha)
                        + " | Sala " + f.getSala().getNumeroSala());
        configurar(cbProducto, controller.listarProductos(), IProducto::getNombre);
        configurar(cbAsiento, List.of(), a -> a.getFila() + "-" + a.getNumero());
        cbTipo.getItems().setAll("Entrada", "Producto");
        cbTipoConfig.getItems().setAll("Entrada", "Producto");
        cbTipo.setValue("Entrada");
        cbTipoConfig.setValue("Entrada");
        cambiarTipo();
        cambiarTipoConfig();
        consultar();
    }

    @FXML private void consultar() {
        Cliente cliente = cbCliente.getValue();
        if (cliente == null) {
            lblSaldo.setText("Puntos disponibles: 0");
            taPuntos.setText("Selecciona un cliente para consultar sus puntos.");
        } else {
            lblSaldo.setText("Puntos disponibles: " + cliente.consultarPuntos());
            StringBuilder texto = new StringBuilder();
            if (cliente.getPuntos() != null) {
                for (RegistroPuntos registro : cliente.getPuntos().getRegistroPuntos()) {
                    texto.append(registro.getPuntos()).append(" puntos | Obtenidos: ")
                            .append(registro.getFechaObtencion()).append(" | Vencen: ")
                            .append(registro.getFechaVencimiento()).append('\n');
                }
            }
            taPuntos.setText(texto.isEmpty() ? "No hay registros de puntos disponibles." : texto.toString());
        }
        actualizarEstado();
    }

    @FXML private void cambiarTipo() {
        boolean entrada = "Entrada".equals(cbTipo.getValue());
        panelEntrada.setVisible(entrada); panelEntrada.setManaged(entrada);
        panelProducto.setVisible(!entrada); panelProducto.setManaged(!entrada);
        actualizarEstado();
    }

    @FXML private void cargarAsientos() {
        cbAsiento.getSelectionModel().clearSelection();
        cbAsiento.getItems().setAll(controller.listarAsientos(cbFuncion.getValue()).stream()
                .filter(Asiento::estaDisponible).toList());
        actualizarEstado();
    }

    private int costoSeleccionado() {
        return "Entrada".equals(cbTipo.getValue())
                ? controller.consultarCostoEntrada(cbFuncion.getValue())
                : controller.consultarCostoProducto(cbProducto.getValue());
    }

    @FXML private void actualizarEstado() {
        int costo = costoSeleccionado();
        lblCosto.setText(costo > 0 ? "Costo del beneficio: " + costo + " puntos"
                : "Selecciona un beneficio con costo configurado.");
        Cliente cliente = cbCliente.getValue();
        boolean entrada = "Entrada".equals(cbTipo.getValue());
        Funcion funcion = cbFuncion.getValue();
        Asiento asiento = cbAsiento.getValue();
        boolean disponible = entrada
                ? funcion != null && funcion.isActivo()
                    && funcion.getFechaHora().isAfter(LocalDateTime.now())
                    && asiento != null && asiento.estaDisponible()
                : cbProducto.getValue() != null;
        btnCanjear.setDisable(cliente == null || costo <= 0 || !disponible
                || cliente.consultarPuntos() < costo);
    }

    @FXML private void cambiarTipoConfig() {
        List<Object> beneficios = new ArrayList<>();
        if ("Entrada".equals(cbTipoConfig.getValue())) {
            beneficios.addAll(controller.listarFunciones().stream()
                    .filter(f -> f.getFechaHora().isAfter(LocalDateTime.now())).toList());
        } else {
            beneficios.addAll(controller.listarProductos());
        }
        configurar(cbBeneficioConfig, beneficios, objeto -> objeto instanceof Funcion f
                ? f.getPelicula().getTitulo() + " | " + f.getFechaHora().format(fecha)
                : ((IProducto) objeto).getNombre());
        txtCostoConfig.clear();
    }

    @FXML private void mostrarCostoConfig() {
        Object beneficio = cbBeneficioConfig.getValue();
        int costo = beneficio instanceof Funcion f ? controller.consultarCostoEntrada(f)
                : beneficio instanceof IProducto p ? controller.consultarCostoProducto(p) : 0;
        txtCostoConfig.setText(costo > 0 ? Integer.toString(costo) : "");
    }

    @FXML private void guardarCosto() {
        ejecutar(() -> {
            Object beneficio = Objects.requireNonNull(cbBeneficioConfig.getValue(), "Selecciona un beneficio");
            int costo = entero(txtCostoConfig);
            if (beneficio instanceof Funcion f) controller.configurarCostoEntrada(f, costo);
            else controller.configurarCostoProducto((IProducto) beneficio, costo);
            actualizarEstado();
            informar("Costo en puntos guardado");
        });
    }

    @FXML private void canjear() {
        ejecutar(() -> {
            Cliente cliente = Objects.requireNonNull(cbCliente.getValue(), "Selecciona un cliente");
            int costo = costoSeleccionado();
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Canjear el beneficio por " + costo + " puntos para " + cliente.getNombre() + "?",
                    ButtonType.OK, ButtonType.CANCEL);
            if (confirmacion.showAndWait().filter(ButtonType.OK::equals).isEmpty()) return;
            String mensaje = "Entrada".equals(cbTipo.getValue())
                    ? controller.canjearEntrada(cliente, cbFuncion.getValue(), cbAsiento.getValue())
                    : controller.canjearProducto(cliente, cbProducto.getValue());
            cargarAsientos();
            consultar();
            informar(mensaje);
        });
    }
}
