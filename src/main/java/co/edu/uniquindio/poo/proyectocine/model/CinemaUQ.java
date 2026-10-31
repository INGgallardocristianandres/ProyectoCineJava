package co.edu.uniquindio.poo.proyectocine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  Clase central Implementa Singleton para mantener un único cinema. */
public final class CinemaUQ {
    private static final CinemaUQ INSTANCIA = new CinemaUQ();
    private final List<Funcion> cartelera = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();

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

    public void iniciarSistema() {
        cartelera.removeIf(funcion -> funcion == null || !funcion.isActivo());
    }

    public void mostrarCartelera() {
        cartelera.stream().filter(Funcion::isActivo).forEach(f ->
                System.out.println(f.getPelicula().getTitulo() + " - " + f.getFechaHora()));
    }
}
