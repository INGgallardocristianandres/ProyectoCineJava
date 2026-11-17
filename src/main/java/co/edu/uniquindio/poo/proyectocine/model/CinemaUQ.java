package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase central. Implementa Singleton para mantener un único cinema.
 */
public final class CinemaUQ {
    private static final CinemaUQ INSTANCIA = new CinemaUQ();
    private final List<Funcion> cartelera = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Administrador> administradores = new ArrayList<>();
    private final List<Pelicula> peliculas = new ArrayList<>();
    private final List<Sala> salas = new ArrayList<>();
    private final List<IProducto> productos = new ArrayList<>();

    private CinemaUQ() { }

    public static CinemaUQ getInstancia() {
        return INSTANCIA;
    }

    public void agregarFuncion(Funcion funcion) {
        if (funcion != null && !cartelera.contains(funcion)) cartelera.add(funcion);
    }

    public void eliminarFuncion(Funcion funcion) {
        cartelera.remove(funcion);
    }

    public List<Funcion> getCartelera() {
        return Collections.unmodifiableList(cartelera);
    }

    public void registrarCliente(Cliente cliente) {
        if (cliente != null && !clientes.contains(cliente)) clientes.add(cliente);
    }

    public List<Cliente> getClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public void registrarAdministrador(Administrador admin) {
        if (admin != null && !administradores.contains(admin)) administradores.add(admin);
    }

    public List<Administrador> getAdministradores() {
        return Collections.unmodifiableList(administradores);
    }

    public void agregarPelicula(Pelicula pelicula) {
        if (pelicula != null && !peliculas.contains(pelicula)) peliculas.add(pelicula);
    }

    public void eliminarPelicula(Pelicula pelicula) {
        peliculas.remove(pelicula);
    }

    public List<Pelicula> getPeliculas() {
        return Collections.unmodifiableList(peliculas);
    }

    public void agregarSala(Sala sala) {
        if (sala != null && salas.stream().noneMatch(s -> s.getNumeroSala() == sala.getNumeroSala())) {
            salas.add(sala);
        }
    }

    public List<Sala> getSalas() {
        return Collections.unmodifiableList(salas);
    }

    public void agregarProducto(IProducto producto) {
        if (producto != null && !productos.contains(producto)) productos.add(producto);
    }

    public List<IProducto> getProductos() {
        return Collections.unmodifiableList(productos);
    }

    public Usuario autenticar(String correo, String contrasenia) {
        if (correo == null || contrasenia == null) return null;
        for (Administrador admin : administradores) {
            if (admin.iniciarSesion(correo.trim(), contrasenia)) return admin;
        }
        for (Cliente cliente : clientes) {
            if (cliente.iniciarSesion(correo.trim(), contrasenia)) return cliente;
        }
        return null;
    }

    public void iniciarSistema() {
        cartelera.removeIf(funcion -> funcion == null || !funcion.isActivo());
    }

    public void mostrarCartelera() {
        cartelera.stream().filter(Funcion::isActivo).forEach(f ->
                System.out.println(f.getPelicula().getTitulo() + " - " + f.getFechaHora()));
    }
}