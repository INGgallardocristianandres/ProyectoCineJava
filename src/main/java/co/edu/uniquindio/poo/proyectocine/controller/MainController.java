package co.edu.uniquindio.poo.proyectocine.controller;

import co.edu.uniquindio.poo.proyectocine.model.*;
import java.time.LocalDateTime;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {
    private static final MainController INSTANCIA = new MainController();
    private final List<Pelicula> peliculas = new ArrayList<>();
    private final List<Sala> salas = new ArrayList<>();
    private final List<IProducto> productos = new ArrayList<>();
    public static MainController getInstancia() { return INSTANCIA; }
    public List<Pelicula> listarPeliculas() {
        for (Funcion f : listarFunciones()) if (!peliculas.contains(f.getPelicula())) peliculas.add(f.getPelicula());
        return List.copyOf(peliculas);
    }
    public List<Sala> listarSalas() {
        for (Funcion f : listarFunciones())
            if (salas.stream().noneMatch(s -> s.getNumeroSala() == f.getSala().getNumeroSala())) salas.add(f.getSala());
        return List.copyOf(salas);
    }
    public List<IProducto> listarProductos() { return List.copyOf(productos); }
    public Pelicula registrarPelicula(String titulo, GeneroPelicula genero,
            ClasificacionPelicula clasificacion, int duracion) {
        Objects.requireNonNull(genero, "Selecciona un género");
        Objects.requireNonNull(clasificacion, "Selecciona una clasificación");
        if (duracion <= 0) throw new IllegalArgumentException("La duración debe ser positiva");
        Pelicula p = new Pelicula(UUID.randomUUID().toString(), texto(titulo, "Título"), genero, duracion, clasificacion);
        peliculas.add(p);
        return p;
    }
    public Sala registrarSala(int numero, int capacidad) {
        if (numero <= 0 || capacidad <= 0) throw new IllegalArgumentException("Sala y capacidad deben ser positivas");
        if (listarSalas().stream().anyMatch(s -> s.getNumeroSala() == numero))
            throw new IllegalArgumentException("El número de sala ya está registrado");
        Sala s = new Sala(numero, capacidad);
        salas.add(s);
        return s;
    }
    public IProducto registrarProducto(String nombre, double precio) {
        IProducto p = crearProducto(nombre, precio);
        productos.add(p);
        return p;
    }
    public Funcion programarFuncion(Pelicula pelicula, Sala sala, LocalDateTime fecha, double precio) {
        Objects.requireNonNull(pelicula, "Selecciona una película");
        Objects.requireNonNull(sala, "Selecciona una sala");
        if (fecha == null || !fecha.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("La función debe ser futura");
        positivo(precio, "Precio");
        LocalDateTime fin = fecha.plusMinutes(pelicula.getDuracionMinutos());
        for (Funcion f : listarFunciones()) {
            if (f.getSala().getNumeroSala() == sala.getNumeroSala()
                    && fecha.isBefore(f.getFechaHora().plusMinutes(f.getPelicula().getDuracionMinutos()))
                    && f.getFechaHora().isBefore(fin))
                throw new IllegalArgumentException("El horario se superpone con otra función de la sala");
        }
        // Asiento guarda la ocupación: las instancias se aíslan por función.
        Sala salaFuncion = new Sala(sala.getNumeroSala(), sala.getCapacidad());
        Funcion f = new Funcion(UUID.randomUUID().toString(), fecha, precio, pelicula, salaFuncion, true);
        cinema.agregarFuncion(f);
        return f;
    }
    private final CinemaUQ cinema = CinemaUQ.getInstancia();
    private final CompraFacade compras;

    public MainController() {
        this(new CompraFacade(new PasarelaPagoAdapter(new PasarelaExterna(
                System.getenv("CINEMA_API_KEY"), System.getenv("CINEMA_URL")))));
    }

    public MainController(CompraFacade compras) {
        this.compras = Objects.requireNonNull(compras);
    }

    public List<Cliente> listarClientes() {
        return List.copyOf(cinema.getClientes());
    }

    public List<Funcion> listarFunciones() {
        return cinema.getCartelera().stream().filter(Funcion::isActivo).toList();
    }

    public Cliente registrarCliente(String nombre, String correo,
                                    String contrasenia, String telefono) {
        nombre = texto(nombre, "Nombre");
        correo = texto(correo, "Correo").toLowerCase(Locale.ROOT);
        texto(contrasenia, "Contraseña");
        telefono = texto(telefono, "Teléfono");
        for (Cliente cliente : cinema.getClientes()) {
            if (correo.equalsIgnoreCase(cliente.getCorreo())) {
                throw new IllegalArgumentException("El correo ya está registrado");
            }
        }
        Cliente cliente = new Cliente(UUID.randomUUID().toString(), nombre,
                correo, contrasenia, telefono);
        cliente.setTarjetaVirtual(new TarjetaVirtual(
                UUID.randomUUID().toString(), 0, EstadoTarjeta.ACTIVA));
        cliente.setPuntos(new CuentaPuntos(0));
        cinema.registrarCliente(cliente);
        return cliente;
    }

    public Funcion programarFuncion(String titulo, GeneroPelicula genero,
            ClasificacionPelicula clasificacion, int duracion, int numeroSala,
            int capacidad, LocalDateTime fecha, double precio) {
        titulo = texto(titulo, "Título");
        Objects.requireNonNull(genero, "Selecciona un género");
        Objects.requireNonNull(clasificacion, "Selecciona una clasificación");
        if (duracion <= 0 || numeroSala <= 0 || capacidad <= 0) {
            throw new IllegalArgumentException(
                    "Duración, sala y capacidad deben ser mayores que cero");
        }
        if (fecha == null || !fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La función debe ser futura");
        }
        positivo(precio, "Precio");
        LocalDateTime fin = fecha.plusMinutes(duracion);
        for (Funcion existente : listarFunciones()) {
            if (existente.getSala().getNumeroSala() == numeroSala) {
                if (existente.getSala().getCapacidad() != capacidad) {
                    throw new IllegalArgumentException(
                            "La capacidad no coincide con la sala registrada");
                }
                LocalDateTime finExistente = existente.getFechaHora()
                        .plusMinutes(existente.getPelicula().getDuracionMinutos());
                if (fecha.isBefore(finExistente)
                        && existente.getFechaHora().isBefore(fin)) {
                    throw new IllegalArgumentException(
                            "El horario se superpone con otra función de la sala");
                }
            }
        }
        Pelicula pelicula = new Pelicula(UUID.randomUUID().toString(), titulo,
                genero, duracion, clasificacion);
        // El modelo almacena el estado en Asiento. Cada función necesita
        // sus propias instancias para no compartir la ocupación.
        Sala sala = new Sala(numeroSala, capacidad);
        Funcion funcion = new Funcion(UUID.randomUUID().toString(), fecha,
                precio, pelicula, sala, true);
        cinema.agregarFuncion(funcion);
        return funcion;
    }

    public List<Asiento> listarAsientos(Funcion funcion) {
        return funcion == null ? List.of() : funcion.getSala().getAsientos();
    }

    public IProducto crearProducto(String nombre, double precio) {
        return new Producto(texto(nombre, "Producto"), positivo(precio, "Precio"));
    }

    public Compra comprar(Cliente cliente, Funcion funcion,
                          List<Asiento> asientos, List<IProducto> productos) {
        if (cliente == null || !cinema.getClientes().contains(cliente)) {
            throw new IllegalArgumentException("Selecciona un cliente registrado");
        }
        if (funcion == null || !listarFunciones().contains(funcion)
                || !funcion.getFechaHora().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Selecciona una función futura y activa");
        }
        if (asientos == null || asientos.isEmpty()
                || new HashSet<>(asientos).size() != asientos.size()) {
            throw new IllegalArgumentException("Selecciona asientos sin repetir");
        }
        for (Asiento asiento : asientos) {
            if (asiento == null || !listarAsientos(funcion).contains(asiento)
                    || !asiento.estaDisponible()) {
                throw new IllegalArgumentException("Hay asientos inválidos u ocupados");
            }
        }
        Objects.requireNonNull(productos, "La lista de productos es obligatoria");
        List<IProducto> copia = new ArrayList<>();
        double total = asientos.size() * funcion.getPrecioEntrada();
        for (IProducto producto : productos) {
            Objects.requireNonNull(producto, "Hay productos inválidos");
            double precio = positivo(producto.getPrecio(), "Precio del producto");
            copia.add(new Producto(producto.getNombre(), precio));
            total += precio;
        }
        positivo(total, "Total");
        if (!compras.procesarCompra(cliente, funcion, List.copyOf(asientos), copia)) {
            throw new IllegalStateException(
                    "Compra rechazada. Revisa el estado y saldo de la tarjeta");
        }
        List<Compra> historial = cliente.getHistorialcompras();
        return historial.get(historial.size() - 1);
    }

    public void recargar(Cliente cliente, double monto) {
        if (cliente == null || !cinema.getClientes().contains(cliente)
                || cliente.getTarjetaVirtual() == null) {
            throw new IllegalArgumentException("Selecciona un cliente con tarjeta");
        }
        positivo(monto, "Recarga");
        if (!Double.isFinite(cliente.getTarjetaVirtual().getSaldo() + monto)) {
            throw new IllegalArgumentException("El saldo resultante es inválido");
        }
        cliente.getTarjetaVirtual().abonar(monto);
    }

    public String consultarCuenta(Cliente cliente) {
        if (cliente == null) return "Selecciona un cliente";
        StringBuilder texto = new StringBuilder("Cliente: ")
                .append(cliente.getNombre()).append('\n');
        TarjetaVirtual tarjeta = cliente.getTarjetaVirtual();
        if (tarjeta != null) {
            texto.append("Saldo: ").append(tarjeta.consultarSaldo())
                    .append(" | Estado: ").append(tarjeta.getEstado()).append('\n');
            for (MovimientoTarjeta movimiento : tarjeta.getMovimientos()) {
                texto.append(movimiento.getFechaMovimiento()).append(" | ")
                        .append(movimiento.getTipo()).append(" | ")
                        .append(movimiento.getValor()).append('\n');
            }
        }
        texto.append("Puntos: ").append(cliente.consultarPuntos())
                .append("\nCompras:\n");
        for (Compra compra : cliente.getHistorialcompras()) {
            texto.append(compra.getId()).append(" | ")
                    .append(compra.getFechaHora()).append(" | ")
                    .append(compra.getValorTotal()).append(" | ")
                    .append(compra.getEstadoCompra().getClass().getSimpleName())
                    .append('\n');
        }
        return texto.toString();
    }

    public String generarReporte(String formato) {
        StringBuilder datos = new StringBuilder("Reporte CinemaUQ\n");
        for (Cliente cliente : listarClientes()) {
            datos.append(consultarCuenta(cliente)).append('\n');
        }
        ReporteFactory factory = switch (texto(formato, "Formato")) {
            case "PDF" -> new ReportePDF();
            case "TXT" -> new ReporteTXT();
            default -> throw new IllegalArgumentException("Formato inválido");
        };
        factory.exportarReporte(datos.toString(), "Cuenta y compras");
        return datos.toString();
    }

    private String texto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio");
        }
        return valor.trim();
    }

    private double positivo(double valor, String campo) {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException(campo + " debe ser mayor que cero");
        }
        return valor;
    }
    @FXML private StackPane contenido;
    @FXML private javafx.scene.layout.VBox menuLateral;

    @FXML private void initialize() { mostrar("Cliente"); }
    @FXML private void abrirClientes() { mostrar("Cliente"); }
    @FXML private void abrirPeliculas() { mostrar("Pelicula"); }
    @FXML private void abrirSalas() { mostrar("Sala"); }
    @FXML private void abrirFunciones() { mostrar("Funcion"); }
    @FXML private void abrirCompras() { mostrar("Compra"); }
    @FXML private void abrirConfiteria() { mostrar("Confiteria"); }
    @FXML private void abrirTarjetas() { mostrar("TarjetaVirtual"); }
    @FXML private void abrirPuntos() { mostrar("Puntos"); }
    @FXML private void abrirReportes() { mostrar("Reporte"); }
    private void mostrar(String pantalla) {
        try {
            var recurso = getClass().getResource("/co/edu/uniquindio/poo/proyectocine/" + pantalla + "View.fxml");
            if (recurso == null) throw new IOException("No se encontró la pantalla " + pantalla);
            Parent vista = FXMLLoader.load(recurso);
            contenido.getChildren().setAll(vista);
            for (javafx.scene.Node nodo : menuLateral.getChildren()) {
                if (nodo instanceof javafx.scene.control.Button boton) {
                    boton.getStyleClass().remove("nav-active");
                    if (pantalla.equals(boton.getUserData())) {
                        boton.getStyleClass().add("nav-active");
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo abrir la pantalla " + pantalla, e);
        }
    }
}
