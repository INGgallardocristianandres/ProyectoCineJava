package co.edu.uniquindio.poo.proyectocine.controller;

import co.edu.uniquindio.poo.proyectocine.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class MainController {
    private static boolean datosInicializados = false;
    private static final MainController INSTANCIA = new MainController();
    private final CinemaUQ cinema = CinemaUQ.getInstancia();
    private final CompraFacade compras;
    private final HistorialComandos historialComandos = new HistorialComandos();
    private Usuario usuarioActual;

    @FXML private StackPane contenido;
    @FXML private VBox menuLateral;
    @FXML private Label lblUsuarioActivo;

    public static MainController getInstancia() {
        return INSTANCIA;
    }

    public MainController() {
        this(new CompraFacade(new PasarelaPagoAdapter(new PasarelaExterna(
                "demo-key", "https://pagos.cinemauq.edu.co"))));
    }

    public MainController(CompraFacade compras) {
        this.compras = Objects.requireNonNull(compras);
        inicializarDatosPrueba();
    }

    private synchronized void inicializarDatosPrueba() {
        if (datosInicializados || !cinema.getAdministradores().isEmpty()) return;
        datosInicializados = true;

        Administrador admin = new Administrador("ADM-1", "Admin CinemaUQ",
                "admin@cinemauq.edu.co", "admin123", "TOTAL");
        cinema.registrarAdministrador(admin);

        Cliente ana = registrarCliente("Ana García", "ana@correo.com", "1234", "3101234567");
        ana.getTarjetaVirtual().abonar(100000, admin);
        ana.getPuntos().acumularPuntos(250);

        Cliente carlos = registrarCliente("Carlos Pérez", "carlos@correo.com", "1234", "3209876543");
        carlos.getTarjetaVirtual().abonar(40000, admin);
        carlos.getPuntos().acumularPuntos(120);

        Pelicula p1 = registrarPelicula("Beyond Horizons", GeneroPelicula.CIENCIA_FICCION,
                ClasificacionPelicula.MAYORES_12, 135);
        Pelicula p2 = registrarPelicula("Echoes", GeneroPelicula.SUSPENSO,
                ClasificacionPelicula.MAYORES_15, 110);

        Sala s1 = registrarSala(1, 20);
        Sala s2 = registrarSala(2, 16);

        // Función en 48 horas (permite probar reembolso del 100%)
        programarFuncion(p1, s1, LocalDateTime.now().plusHours(48), 16000);
        // Función en 5 horas (permite probar reembolso del 80%)
        programarFuncion(p2, s2, LocalDateTime.now().plusHours(5), 14000);

        IProducto palomitas = registrarProducto("Palomitas Grandes", 12000);
        IProducto gaseosa = registrarProducto("Gaseosa 500ml", 6000);
        registrarProducto("Nachos con Salsa", 10000);

        Combo comboPareja = new ComboBuilder()
                .conNombre("Combo Pareja (Palomitas + Gaseosa)")
                .conDescuento(10.0)
                .agregarProducto(palomitas)
                .agregarProducto(gaseosa)
                .build();
        cinema.agregarProducto(comboPareja);
    }

    // --- AUTENTICACIÓN Y ROLES ---
    public Usuario iniciarSesion(String correo, String contrasenia) {
        Usuario u = cinema.autenticar(texto(correo, "Correo"), texto(contrasenia, "Contraseña"));
        if (u == null) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
        INSTANCIA.usuarioActual = u;
        this.usuarioActual = u;
        return u;
    }

    public void cerrarSesion() {
        if (getUsuarioActual() != null) {
            getUsuarioActual().cerrarSesion();
        }
        INSTANCIA.usuarioActual = null;
        this.usuarioActual = null;
        if (INSTANCIA.menuLateral != null) {
            INSTANCIA.actualizarMenuPorRol();
            INSTANCIA.mostrar("Login");
        }
    }

    public Usuario getUsuarioActual() {
        return (INSTANCIA != null && INSTANCIA.usuarioActual != null)
                ? INSTANCIA.usuarioActual
                : this.usuarioActual;
    }

    public boolean esAdministrador() {
        return getUsuarioActual() instanceof Administrador;
    }

    public Cliente getClienteActual() {
        return getUsuarioActual() instanceof Cliente c ? c : null;
    }

    // --- LISTADOS ---
    public List<Pelicula> listarPeliculas() { return cinema.getPeliculas(); }
    public List<Sala> listarSalas() { return cinema.getSalas(); }
    public List<IProducto> listarProductos() { return cinema.getProductos(); }
    public List<Cliente> listarClientes() { return cinema.getClientes(); }
    public List<Funcion> listarFunciones() {
        return cinema.getCartelera().stream().filter(Funcion::isActivo).toList();
    }
    public List<Asiento> listarAsientos(Funcion funcion) {
        return funcion == null ? List.of() : funcion.getSala().getAsientos();
    }

    // --- REGISTROS ADMINISTRATIVOS (CON PATRÓN COMMAND) ---
    public Pelicula registrarPelicula(String titulo, GeneroPelicula genero,
                                      ClasificacionPelicula clasificacion, int duracion) {
        Objects.requireNonNull(genero, "Selecciona un género");
        Objects.requireNonNull(clasificacion, "Selecciona una clasificación");
        if (duracion <= 0) throw new IllegalArgumentException("La duración debe ser positiva");
        Pelicula p = new Pelicula(UUID.randomUUID().toString(), texto(titulo, "Título"),
                genero, duracion, clasificacion);
        cinema.agregarPelicula(p);

        if (getUsuarioActual() instanceof Administrador admin) {
            historialComandos.ejecutarComando(new IComando() {
                @Override public void ejecutar() { admin.gestionarPelicula(p, "registrar " + p.getTitulo()); }
                @Override public void deshacer() { cinema.eliminarPelicula(p); }
            });
        }
        return p;
    }

    public Sala registrarSala(int numero, int capacidad) {
        if (numero <= 0 || capacidad <= 0) throw new IllegalArgumentException("Sala y capacidad deben ser positivas");
        if (listarSalas().stream().anyMatch(s -> s.getNumeroSala() == numero))
            throw new IllegalArgumentException("El número de sala ya está registrado");
        Sala s = new Sala(numero, capacidad);
        cinema.agregarSala(s);
        return s;
    }

    public IProducto registrarProducto(String nombre, double precio) {
        IProducto p = new Producto(texto(nombre, "Producto"), positivo(precio, "Precio"));
        cinema.agregarProducto(p);
        return p;
    }

    public Combo registrarCombo(String nombre, double descuento, List<IProducto> seleccionados) {
        Combo combo = new ComboBuilder()
                .conNombre(texto(nombre, "Nombre del combo"))
                .conDescuento(descuento)
                .agregarProductos(seleccionados)
                .build();
        cinema.agregarProducto(combo);
        return combo;
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
        Sala salaFuncion = new Sala(sala.getNumeroSala(), sala.getCapacidad());
        Funcion f = new Funcion(UUID.randomUUID().toString(), fecha, precio, pelicula, salaFuncion, true);
        cinema.agregarFuncion(f);

        if (getUsuarioActual() instanceof Administrador admin) {
            historialComandos.ejecutarComando(new IComando() {
                @Override public void ejecutar() { admin.gestionarFuncion(f, "programar " + f.getId()); }
                @Override public void deshacer() { cinema.eliminarFuncion(f); }
            });
        }
        return f;
    }

    public void deshacerUltimaAccionAdmin() {
        historialComandos.deshacer();
    }

    // --- CLIENTES Y RECARGAS ---
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
        Cliente cliente = new Cliente(UUID.randomUUID().toString(), nombre, correo, contrasenia, telefono);
        cliente.setTarjetaVirtual(new TarjetaVirtual(UUID.randomUUID().toString(), 0, EstadoTarjeta.ACTIVA));
        cliente.setPuntos(new CuentaPuntos(0));
        cinema.registrarCliente(cliente);
        return cliente;
    }

    public void recargar(Cliente cliente, double monto) {
        if (cliente == null || cliente.getTarjetaVirtual() == null) {
            throw new IllegalArgumentException("Selecciona un cliente con tarjeta");
        }
        positivo(monto, "Recarga");
        Administrador admin = getUsuarioActual() instanceof Administrador a ? a : null;
        cliente.getTarjetaVirtual().abonar(monto, admin);
    }

    // --- COMPRAS, CANCELACIONES Y REEMBOLSOS ---
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
            if (asiento == null || !listarAsientos(funcion).contains(asiento) || !asiento.estaDisponible()) {
                throw new IllegalArgumentException("Hay asientos inválidos u ocupados");
            }
        }
        Objects.requireNonNull(productos, "La lista de productos es obligatoria");
        List<IProducto> copia = new ArrayList<>();
        for (IProducto producto : productos) {
            Objects.requireNonNull(producto, "Hay productos inválidos");
            double precio = positivo(producto.getPrecio(), "Precio del producto");
            copia.add(new Producto(producto.getNombre(), precio));
        }
        if (!compras.procesarCompra(cliente, funcion, List.copyOf(asientos), copia)) {
            throw new IllegalStateException("Compra rechazada. Revisa el estado y saldo de la tarjeta");
        }
        List<Compra> historial = cliente.getHistorialcompras();
        return historial.get(historial.size() - 1);
    }

    /**
     * Cancela una compra aplicando la estrategia de reembolso según las horas restantes:
     * > 24h: 100% | 2h a 24h: 80% | < 2h: No permitido.
     */
    public double cancelarCompra(Compra compra) {
        if (compra == null) throw new IllegalArgumentException("Selecciona una compra para cancelar");
        if (!(compra.getEstadoCompra() instanceof CompraPagada)) {
            throw new IllegalStateException("Solo pueden cancelarse compras pagadas que no hayan sido reembolsadas");
        }
        if (compra.getEntradas().isEmpty() || compra.getEntradas().get(0).getFuncion() == null) {
            throw new IllegalStateException("La compra no tiene una función asociada");
        }

        Funcion funcion = compra.getEntradas().get(0).getFuncion();
        double horasRestantes = Duration.between(LocalDateTime.now(), funcion.getFechaHora()).toMinutes() / 60.0;

        IEstrategiaRembolso estrategia;
        if (horasRestantes > 24.0) {
            estrategia = new RembolsoCompletoStrategy();
        } else if (horasRestantes >= 2.0) {
            estrategia = new RembolsoParcialStrategy();
        } else {
            estrategia = new SinRembolsoStrategy();
            estrategia.calcularRembolso(compra.getValorTotal(), horasRestantes);
            throw new IllegalStateException("No se permite cancelar con menos de 2 horas de anticipación");
        }

        double montoReembolso = estrategia.calcularRembolso(compra.getValorTotal(), horasRestantes);
        compra.cancelar(); // Cambia el State a CompraCancelada (impide doble reembolso)

        if (montoReembolso > 0 && compra.getCliente().getTarjetaVirtual() != null) {
            compra.getCliente().getTarjetaVirtual().abonar(montoReembolso);
        }
        if (funcion.isActivo()) {
            for (Entrada entrada : compra.getEntradas()) {
                entrada.getAsiento().cambiarEstado(EstadoAsiento.DISPONIBLE);
            }
        }
        return montoReembolso;
    }

    /**
     * Cancela una función por parte del cine y reembolsa el 100% a todos los clientes afectados.
     */
    public int cancelarFuncionPorCine(Funcion funcion) {
        if (funcion == null || !funcion.isActivo()) {
            throw new IllegalArgumentException("Selecciona una función activa");
        }
        funcion.cancelarFuncion();
        IEstrategiaRembolso estrategiaCine = new RembolsoCompletoStrategy();
        int comprasReembolsadas = 0;

        for (Cliente cliente : cinema.getClientes()) {
            for (Compra compra : cliente.getHistorialcompras()) {
                if (compra.getEstadoCompra() instanceof CompraPagada
                        && !compra.getEntradas().isEmpty()
                        && funcion.equals(compra.getEntradas().get(0).getFuncion())) {
                    double reembolso = estrategiaCine.calcularRembolso(compra.getValorTotal(), 0);
                    compra.cancelar();
                    if (reembolso > 0 && cliente.getTarjetaVirtual() != null) {
                        cliente.getTarjetaVirtual().abonar(reembolso);
                    }
                    comprasReembolsadas++;
                }
            }
        }
        if (getUsuarioActual() instanceof Administrador admin) {
            admin.cancelarFuncion(funcion);
        }
        return comprasReembolsadas;
    }

    // --- REDENCIÓN DE PUNTOS ---
    public String redimirPuntos(Cliente cliente, String tipoBeneficio,
                                Funcion funcion, Asiento asiento, IProducto producto) {
        if (cliente == null) throw new IllegalArgumentException("Selecciona un cliente");
        int puntosRequeridos = switch (tipoBeneficio.toUpperCase(Locale.ROOT)) {
            case "ENTRADA" -> {
                if (funcion == null || asiento == null || !asiento.estaDisponible()) {
                    throw new IllegalArgumentException("Selecciona una función y un asiento disponible");
                }
                yield 100;
            }
            case "COMBO" -> {
                if (!(producto instanceof Combo)) {
                    throw new IllegalArgumentException("Selecciona un combo válido");
                }
                yield 90;
            }
            case "PRODUCTO" -> {
                if (producto == null) {
                    throw new IllegalArgumentException("Selecciona un producto válido");
                }
                yield 50;
            }
            default -> throw new IllegalArgumentException("Tipo de beneficio inválido");
        };

        String beneficioModelo = "COMBO".equalsIgnoreCase(tipoBeneficio) ? "PRODUCTO" : tipoBeneficio;
        String resultado = cliente.redimirPuntos(puntosRequeridos, beneficioModelo, funcion, asiento, producto);
        if (resultado.startsWith("No cuenta")) {
            throw new IllegalStateException(resultado + " (Requeridos: " + puntosRequeridos + " pts)");
        }
        return resultado + " (-" + puntosRequeridos + " pts)";
    }

    public String consultarCuenta(Cliente cliente) {
        if (cliente == null) return "Selecciona un cliente";
        StringBuilder texto = new StringBuilder("Cliente: ").append(cliente.getNombre())
                .append(" (").append(cliente.getCorreo()).append(")\n");
        TarjetaVirtual tarjeta = cliente.getTarjetaVirtual();
        if (tarjeta != null) {
            texto.append("Saldo: $").append(String.format("%.2f", tarjeta.consultarSaldo()))
                    .append(" | Estado: ").append(tarjeta.getEstado()).append("\n--- Movimientos ---\n");
            for (MovimientoTarjeta mov : tarjeta.getMovimientos()) {
                texto.append(mov.getFechaMovimiento().withNano(0)).append(" | ")
                        .append(mov.getTipo()).append(" | $")
                        .append(String.format("%.2f", mov.getValor())).append('\n');
            }
        }
        texto.append("Puntos vigentes: ").append(cliente.consultarPuntos()).append("\n--- Compras ---\n");
        for (Compra compra : cliente.getHistorialcompras()) {
            String peli = compra.getEntradas().isEmpty() ? "Solo confitería"
                    : compra.getEntradas().get(0).getFuncion().getPelicula().getTitulo();
            texto.append(peli).append(" | $")
                    .append(String.format("%.2f", compra.getValorTotal())).append(" | ")
                    .append(compra.getEstadoCompra().getClass().getSimpleName()).append('\n');
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
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(campo + " es obligatorio");
        return valor.trim();
    }

    private double positivo(double valor, String campo) {
        if (!Double.isFinite(valor) || valor <= 0) throw new IllegalArgumentException(campo + " debe ser mayor que cero");
        return valor;
    }

    // --- NAVEGACIÓN ---
    // --- NAVEGACIÓN ---
    @FXML private void initialize() {
        if (this != INSTANCIA) {
            INSTANCIA.contenido = this.contenido;
            INSTANCIA.menuLateral = this.menuLateral;
            INSTANCIA.lblUsuarioActivo = this.lblUsuarioActivo;
        }
        INSTANCIA.actualizarMenuPorRol();
        INSTANCIA.mostrar("Login");
    }

    public void alIniciarSesionExitoso() {
        actualizarMenuPorRol();
        if (esAdministrador()) {
            mostrar("Pelicula");
        } else {
            mostrar("Compra");
        }
    }

    private void actualizarMenuPorRol() {
        Usuario u = getUsuarioActual();
        boolean logueado = u != null;
        boolean admin = u instanceof Administrador;
        if (lblUsuarioActivo != null) {
            lblUsuarioActivo.setText(!logueado ? "Sin sesión activa"
                    : (admin ? "ADMIN: " : "CLIENTE: ") + u.getNombre());
        }
        if (menuLateral == null) return;
        for (Node nodo : menuLateral.getChildren()) {
            if (nodo instanceof Button boton && boton.getUserData() != null) {
                String vista = boton.getUserData().toString();
                boolean visible = switch (vista) {
                    case "Login" -> !logueado;
                    case "Salir" -> logueado;
                    case "Pelicula", "Sala", "Funcion", "Confiteria", "TarjetaVirtual", "Reporte" -> logueado && admin;
                    case "Compra", "Cliente", "Puntos" -> logueado && !admin;
                    default -> logueado;
                };
                boton.setVisible(visible);
                boton.setManaged(visible);
            }
        }
    }

    @FXML private void abrirLogin() { mostrar("Login"); }
    @FXML private void salirSesion() { cerrarSesion(); }
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
            for (Node nodo : menuLateral.getChildren()) {
                if (nodo instanceof Button boton) {
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